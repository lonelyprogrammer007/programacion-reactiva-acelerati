package com.example.demo.examples;

import reactor.core.publisher.Flux;
import java.time.Duration;

public class Seven {

    public static void main(String[] args) throws InterruptedException {
        Flux<String> usuarios = Flux.just("Ana", "Juan", "Pedro").delayElements(Duration.ofMillis(200));
        Flux<String> roles = Flux.just("Admin", "Developer", "Tester").delayElements(Duration.ofMillis(300));

        // Combinamos un usuario con un rol. El zip espera a que ambos flujos emitan un elemento.
        Flux<String> usuariosConRoles = usuarios.zipWith(roles,
                (usuario, rol) -> usuario + " tiene el rol de " + rol
        );

        usuariosConRoles.subscribe(System.out::println);

        Thread.sleep(2000);
    }
}