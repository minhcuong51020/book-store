package com.example.shopstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ShopStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopStoreApplication.class, args);
    }

}
