package com.iacbackend.shop.controller;

import com.iacbackend.shop.Exceptions.CategoryNotFoundException;
import com.iacbackend.shop.model.Category;
import com.iacbackend.shop.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewCategory (@RequestParam String name
            , @RequestParam String description
            , @RequestParam String image ) {

        Category c = new Category();
        c.setName(name);
        c.setDescription(description);
        c.setImage(image);
        categoryRepository.save(c);
        return "Category Added";
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Category getCategory (@PathVariable int id) { return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id)); }

    @PutMapping(path="/{id}")
    public @ResponseBody Category updateCategory (@PathVariable int id, @RequestBody Category newCategory) {
        return categoryRepository.findById(id)
            .map(product -> {
                product.setName(newCategory.getName());
                product.setDescription(newCategory.getDescription());
                product.setImage(newCategory.getImage());
                return categoryRepository.save(product);
            })
            .orElseGet(() -> {
                newCategory.setId(id);
                return categoryRepository.save(newCategory);
            });
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteCategory (@PathVariable int id) {
        categoryRepository.deleteById(id);
        return "Category Deleted";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Category> getAllCategories() { return categoryRepository.findAll(); }
}
