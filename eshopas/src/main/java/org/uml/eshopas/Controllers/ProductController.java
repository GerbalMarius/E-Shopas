package org.uml.eshopas.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uml.eshopas.EshopApplication;
import org.uml.eshopas.Models.Category;
import org.uml.eshopas.Repositories.CategoryRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
@RequestMapping("api/product")
public class ProductController {
    private final CategoryRepository categoryRepository;

    public ProductController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> allCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}
