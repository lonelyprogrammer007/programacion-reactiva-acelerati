package com.example.demo.examples;

import reactor.core.publisher.Flux;

public class Two {

    public static void main(String[] args) {
        // 1. Creación de un Flux a partir de una lista de elementos.
        Flux<String> fluxNombres = Flux.just("Ana", "Juan", "Pedro", "Maria");

        // 2. Suscripción para consumir cada uno de los elementos.
        fluxNombres.subscribe(
                nombre -> System.out.println("Hola, " + nombre),
                error -> System.err.println("Error en el flux: " + error),
                () -> System.out.println("El Flux ha terminado de emitir nombres.")
        );
    }
}