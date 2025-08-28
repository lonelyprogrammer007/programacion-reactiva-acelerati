package com.example.demo.examples;

import reactor.core.publisher.Flux;

public class Three {

    public static void main(String[] args) {
        Flux.just("ana", "juan", "pedro")
                .map(nombre -> nombre.toUpperCase()) // Transforma cada nombre a mayúsculas
                .subscribe(nombreEnMayusculas -> System.out.println(nombreEnMayusculas));
    }
}