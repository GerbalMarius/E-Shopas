package org.uml.eshopas.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Collection;

@Entity
@Data
@Table(name = "products")
public final class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price",precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "units")
    private int units;

    @JsonIgnore
    @Lob
    @Column(name = "picture", length = 1000000)
    private byte[] picture;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "discount", precision = 5, scale = 2)
    private BigDecimal discount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Collection<CartProduct> cartProducts;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Manufacturer manufacturer;

    public String getPictureBase64() {
        if (this.picture != null) {
            return Base64.getEncoder().encodeToString(this.picture);
        }
        return null;
    }
}
