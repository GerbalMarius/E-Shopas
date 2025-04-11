package Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "manufacturers")
public final class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;
}
