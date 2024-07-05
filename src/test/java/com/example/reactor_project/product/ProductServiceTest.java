package com.example.reactor_project.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService service;

    private List<ProductDto> productList;
    private Flux<ProductDto> productFlux;

    @BeforeEach
    void setUp(){
        productList = Arrays.asList(
                new ProductDto(1, "Product 1"),
                new ProductDto(2, "Product 2")
        );

        productFlux = Flux.just(
                new ProductDto(1, "Product 1"),
                new ProductDto(2, "Product 2"));
    }

    @Test
    void testLoadAllProducts(){
        when(productDao.getProducts()).thenReturn(productList);
        List<ProductDto> result = service.loadAllProducts();

        assertEquals(productList, result);
        verify(productDao, times(1)).getProducts();
    }

    @Test
    void testLoadAllProductsReactive(){
        when(productDao.getProductsReactive()).thenReturn(productFlux);

        Flux<ProductDto> result = service.loadAllProductsReactive();

        StepVerifier.create(result)
                .expectNextSequence(productList)
                .verifyComplete();
        verify(productDao, times(1)).getProductsReactive();
    }

    @Test
    public void onErrorExample(){

        Flux<String> fluxCalc = Flux.just( -1 , 0, 1)
                .map(i -> "10 / " + i + " = " + (10 / i));

        fluxCalc.subscribe(value -> System.out.println("value: " + value),
                error -> System.out.println("Error: " + error));

        StepVerifier.create(fluxCalc)
                .expectNextCount(1)
                .expectError(ArithmeticException.class)
                .verify();
    }

}
