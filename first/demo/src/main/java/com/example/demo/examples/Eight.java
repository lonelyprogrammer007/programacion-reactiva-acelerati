package com.example.demo.examples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Eight {

    public static void main(String[] args) {
        // --- Ejemplo con onErrorReturn ---
        System.out.println("--- Usando onErrorReturn ---");
        Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("¡Error de red!")))
                .concatWith(Flux.just("D")) // "D" nunca será emitido
                .onErrorReturn("VALOR_POR_DEFECTO")
                .subscribe(letra -> System.out.println("Recibido: " + letra));

        System.out.println("\n--- Usando onErrorResume ---");
        // --- Ejemplo con onErrorResume ---
        Flux.just(10, 20, 0, 40)
                .flatMap(divisor -> dividirPor(100, divisor))
                .onErrorResume(error -> {
                    System.err.println("Error capturado: " + error.getMessage() + ". Cambiando a un flujo de respaldo.");
                    return Flux.just(-1, -2, -3); // Flujo alternativo
                })
                .subscribe(resultado -> System.out.println("Resultado: " + resultado));
    }

    // Un método que devuelve un Mono, pero puede fallar.
    public static Mono<Integer> dividirPor(int dividendo, int divisor) {
        if (divisor == 0) {
            return Mono.error(new IllegalArgumentException("No se puede dividir por cero."));
        }
        return Mono.just(dividendo / divisor);
    }
}
