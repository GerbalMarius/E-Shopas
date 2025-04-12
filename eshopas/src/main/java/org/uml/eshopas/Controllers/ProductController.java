package org.uml.eshopas.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.uml.eshopas.DTOS.ProductData;
import org.uml.eshopas.EshopApplication;
import org.uml.eshopas.Models.Product;
import org.uml.eshopas.Repositories.CategoryRepository;
import org.uml.eshopas.Repositories.ManufacturerRepository;
import org.uml.eshopas.Repositories.ProductRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = EshopApplication.REACT_FRONT_URL, allowCredentials = "true")
@RequestMapping("api/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductData>> allProducts() {
        List<ProductData> productDataList = productRepository.findAll().stream()
                .map(ProductData::new) // Convert to DTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDataList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") BigDecimal price,
                                        @RequestParam("units") int units,
                                        @RequestParam("categoryId") long categoryId,
                                        @RequestParam("manufacturerId") long manufacturerId,
                                        @RequestParam("discount") BigDecimal discount,
                                        @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Product p = new Product();
            p.setName(name);
            p.setDescription(description);
            p.setPrice(price);
            p.setUnits(units);
            p.setDiscount(discount);
            p.setCategory(categoryRepository.findById(categoryId).orElseThrow());
            p.setManufacturer(manufacturerRepository.findById(manufacturerId).orElseThrow());

            if (image != null && !image.isEmpty()) {
                // Compress or save image
                byte[] compressedImage = compressImage(image.getBytes(), 250, 250);
                p.setPicture(compressedImage);
            }

            productRepository.save(p);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading product: " + e.getMessage());
        }
    }

    // Method to compress image
    private byte[] compressImage(byte[] imageData, int targetWidth, int targetHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));
        if (originalImage == null) {
            return null;
        }

        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", outputStream);
        return outputStream.toByteArray();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductData> getProduct(@PathVariable Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product product = productOpt.get();
        // Pridedame kategorijos ir gamintojo pavadinimus
        ProductData productData = new ProductData(product);
        productData.setCategoryName(product.getCategory().getTitle()); // Set category name
        productData.setManufacturerName(product.getManufacturer().getName()); // Set manufacturer name
        return ResponseEntity.ok(productData);
    }


}
