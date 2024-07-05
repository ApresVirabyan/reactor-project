package com.example.reactor_project.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductDto> getAllProducts() {

        return service.loadAllProducts();

    }

    @GetMapping(value = "/reactive", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getAllCustomersReactive() {
        return service.loadAllProductsReactive();

    }
}
