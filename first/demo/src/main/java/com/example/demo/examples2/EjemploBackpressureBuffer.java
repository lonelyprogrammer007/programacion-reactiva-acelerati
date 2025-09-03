package com.example.demo.examples2;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class EjemploBackpressureBuffer {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(1)) // Productor muy rápido: emite un número cada 1ms
                .onBackpressureBuffer(10) // Crea un búfer con capacidad para 10 elementos
                .doOnNext(i -> System.out.println("Producido: " + i))
                .publishOn(Schedulers.boundedElastic()) // Cambia el hilo para el consumidor
                .delayElements(Duration.ofMillis(100)) // Consumidor lento: procesa cada 100ms
                .subscribe(
                        item -> System.out.println("--> Consumido: " + item),
                        error -> System.out.println("!!! Error por desbordamiento de búfer: " + error.getMessage())
                );

        Thread.sleep(5000); // Mantenemos la aplicación viva para observar
    }
}
