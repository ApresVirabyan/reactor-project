package com.example.reactor_project.product;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class ProductDao {

    private final List<String> productNames = List.of("Чикен Бургер", "Биг Спешиал Ростбиф", "Плов рыбный",

            "Двойной Чизбургер", "Биг Спешиал", "Чикен Премьер", "Наггетсы", "Тост с ветчиной", "Пирожок с кудябликами",

            "Клецки свекольные");

    public List<ProductDto> getProducts() {
        return IntStream.rangeClosed(1, productNames.size())
                .peek(i -> System.out.println("processing count: " + i))
                .peek(it -> sleepExecution())
                .mapToObj(i -> new ProductDto(i, productNames.get(i -1)))
                .toList();
    }

    public Flux<ProductDto> getProductsReactive(){
       return Flux.range(1, productNames.size())
               .delayElements(Duration.ofSeconds(1))
               .doOnNext(i -> System.out.println("processing count: " + i))
               .map(i -> new ProductDto(i, productNames.get(i -1)));
    }

    private void sleepExecution() {

        try {

            Thread.sleep(1000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }
}
