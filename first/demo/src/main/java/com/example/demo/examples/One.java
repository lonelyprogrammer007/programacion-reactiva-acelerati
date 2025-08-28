package com.example.demo.examples;

import reactor.core.publisher.Mono;

public class One {

    public static void main(String[] args) {
        // 1. Creación de un Mono que emite un único String.
        Mono<String> monoBasico = Mono.just("Hola Mundo Reactivo");

        // 2. Suscripción para consumir el valor.
        // El código dentro de subscribe() solo se ejecuta cuando el Mono emite su valor.
        monoBasico.subscribe(
                data -> System.out.println("Dato recibido: " + data), // onNext: se ejecuta cuando llega el dato
                error -> System.err.println("Ocurrió un error: " + error), // onError: se ejecuta si hay un error
                () -> System.out.println("El Mono ha completado.") // onComplete: se ejecuta al finalizar con éxito
        );
    }
}