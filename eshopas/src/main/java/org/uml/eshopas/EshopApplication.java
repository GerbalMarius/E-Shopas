package org.uml.eshopas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EshopApplication {

    public static final  String REACT_FRONT_URL = "http://localhost:3000";

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);
    }

}
