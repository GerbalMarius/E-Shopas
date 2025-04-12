package org.uml.eshopas.DTOS;

import lombok.Data;
import org.uml.eshopas.Models.Product;
import java.util.Base64;

@Data
public class ProductData {
    private long id;
    private String name;
    private String description;
    private double price;
    private int units;
    private String pictureBase64; // Converted base64 string
    private int categoryId;
    private int manufacturerId;
    private int discount;
    private String categoryName;
    private String manufacturerName;

    public ProductData(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice().doubleValue();
        this.units = product.getUnits();
        this.pictureBase64 = product.getPictureBase64(); // Get base64 string
        this.categoryId = (int) product.getCategory().getId();
        this.manufacturerId = (int) product.getManufacturer().getId();
        this.discount = product.getDiscount() != null ? product.getDiscount().intValue() : 0;
    }
}



