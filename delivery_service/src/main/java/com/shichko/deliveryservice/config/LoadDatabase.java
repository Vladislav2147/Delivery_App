package com.shichko.deliveryservice.config;

import com.shichko.deliveryservice.model.entity.Product;
import com.shichko.deliveryservice.model.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        Product product1 = new Product();
        product1.setName("product1");
        product1.setPrice(20.0);
        product1.setWeight(5.0);

        Product product2 = new Product();
        product2.setName("product2");
        product2.setPrice(2.0);
        product2.setWeight(.5);

        return args -> {
//            repository.deleteAll();
//            log.info("Preloading " + repository.save(product1));
//            log.info("Preloading " + repository.save(product2));

        };
    }
}