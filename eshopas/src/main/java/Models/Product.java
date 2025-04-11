package Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
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
    @Column(name = "picture", length = 1000000)
    private Byte[] picture;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "discount", precision = 5, scale = 2)
    private BigDecimal discount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Collection<CartProduct> cartProducts;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;


}
