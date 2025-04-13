package org.uml.eshopas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableTransactionManagement
public class EshopApplication {

    public static final  String REACT_FRONT_URL = "http://localhost:3000";


    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);
    }

}
