package com.example.reactor_project;

import org.w3c.dom.ls.LSOutput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.time.Duration.ofMillis;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(() -> {
            System.out.println("Hello World");
        });

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello World");
        CompletableFuture<Integer> integerCompletableFuture = future.thenApply(String::length);
        future.thenAccept(System.out::println);

        Flux<String> flux = Flux.just("Hello", "World");
        Flux<String> flux2 = Flux.fromIterable(Arrays.asList("Hello", "World"));
        flux.subscribe(System.out::println,
                error -> System.out.println(error),
                () -> System.out.println("Done"),
                sub -> sub.request(2)
        );

        Mono<List<String>> listMono = flux.collectList();
        
        Flux<List<String>> flux1 = listMono.flux();


        System.out.println("Using flatMap():");
        Flux.range(1, 15).cache()
                .flatMap(item -> Flux.just(item).delayElements(ofMillis(1)))
                .subscribe(x -> System.out.print(x + " "));

        Thread.sleep(100);

        System.out.println("\n\nUsing concatMap():");
        Flux.range(1, 15).cache()
                .concatMap(item -> Flux.just(item).delayElements(ofMillis(1)))
                .subscribe(x -> System.out.print(x + " "));

        Thread.sleep(300);
        System.out.println("\n\nUsing switchMap():");
        Flux.range(1, 15)
                .switchMap(item -> Flux.just(item).delayElements(ofMillis(1)))
                .subscribe(x -> System.out.print(x + " "));

        Thread.sleep(100);
        System.out.println();
        Thread.sleep(200);
        Flux.just(1, 2, 3).reduce(Integer::sum).subscribe(System.out::println);
        Flux.just(1, 2, 3).reduce((a,b) -> a > b ? a : b).subscribe(System.out::println);

        Flux.just(1,2,3,4,5,6,7,8,9, 10).parallel(2).runOn(Schedulers.parallel())
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " " + i));

        Thread.sleep(100);
        Flux.just("a", "b", "c").zipWith(Flux.just(1,2,3), (a,b) -> a + b).subscribe(System.out::println);

    }
}
