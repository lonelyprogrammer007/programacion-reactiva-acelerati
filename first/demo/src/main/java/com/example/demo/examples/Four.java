package com.example.demo.examples;

import reactor.core.publisher.Flux;

public class Four {

    public static void main(String[] args) {
        Flux.range(1, 10) // Emite números del 1 al 10
                .filter(numero -> numero % 2 == 0) // Filtra solo los números pares
                .subscribe(numeroPar -> System.out.println("Número par: " + numeroPar));
    }
}