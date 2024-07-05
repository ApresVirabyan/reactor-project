package com.example.reactor_project.product;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    public List<ProductDto> loadAllProducts() {

        long start = System.currentTimeMillis();
        List<ProductDto> products = productDao.getProducts();
        long end = System.currentTimeMillis();
        System.out.println("Total execution time : " + (end - start));

        return products;
    }

    public Flux<ProductDto> loadAllProductsReactive() {

        long start = System.currentTimeMillis();
        Flux<ProductDto> products = productDao.getProductsReactive();
        long end = System.currentTimeMillis();
        System.out.println("Total execution time : " + (end - start));

        return products;

    }
}
