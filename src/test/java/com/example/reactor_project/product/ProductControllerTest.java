package com.example.reactor_project.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private List<ProductDto> productList;
    private Flux<ProductDto> productFlux;


    @BeforeEach
    void setUp() {

        productList = Arrays.asList(
                new ProductDto(1, "Product 1"),
                new ProductDto(2, "Product 2")
        );

        productFlux = Flux.just(
                new ProductDto(1, "Product 1"),
                new ProductDto(2, "Product 2")
        );
    }

    @Test
    void testGetAllProducts() throws Exception {

        when(productService.loadAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("[{\"id\":1,\"name\":\"Product 1\"},{\"id\":2,\"name\":\"Product 2\"}]"));
    }

    @Test
    void testGetAllProductsReactive() {
        when(productService.loadAllProductsReactive()).thenReturn(productFlux);

        WebTestClient.bindToController(new ProductController(productService))
                .build()
                .get()
                .uri("/products/reactive")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody()
                .as(StepVerifier::create)
                .expectNextSequence(productList)
                .verifyComplete();
    }
}
