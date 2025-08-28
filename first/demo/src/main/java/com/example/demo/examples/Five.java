package com.example.demo.examples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

public class Five {

    // Simula una llamada a una base de datos o API que devuelve los permisos de un usuario.
    public static Flux<String> obtenerPermisos(String usuario) {
        // Simulamos un retraso de red
        return Flux.just(usuario + ":READ", usuario + ":WRITE").delayElements(Duration.ofMillis(100));
    }

    public static void main(String[] args) throws InterruptedException {
        Flux.just("admin", "guest")
                .flatMap(Five::obtenerPermisos) // Por cada usuario, obtenemos sus permisos
                .subscribe(permiso -> System.out.println("Permiso obtenido: " + permiso));

        // Esperamos un poco para que el proceso asíncrono termine (solo para este ejemplo)
        Thread.sleep(1000);
    }
}