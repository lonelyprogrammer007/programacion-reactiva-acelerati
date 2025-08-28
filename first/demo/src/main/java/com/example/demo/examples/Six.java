package com.example.demo.examples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

public class Six {

    // Cliente Web no bloqueante para hacer llamadas HTTP
    private static final WebClient webClient = WebClient.create("https://rickandmortyapi.com/api");

    // 1. Obtiene los primeros N personajes de la API
    public static Flux<JsonNode> obtenerPersonajes(int cantidad) {
        return webClient.get()
                .uri("/character?limit=" + cantidad) // Endpoint para obtener personajes
                .retrieve()
                .bodyToMono(JsonNode.class) // El resultado es un solo JSON
                .flatMapMany(json -> Flux.fromIterable(json.get("results"))); // Lo convertimos en un flujo de personajes
    }

    // 2. Para un personaje dado, obtiene la información de su primer episodio
    public static Mono<JsonNode> obtenerInfoPrimerEpisodio(JsonNode personaje) {
        String nombrePersonaje = personaje.get("name").asText();
        System.out.println("-> Buscando episodio para: " + nombrePersonaje);

        // Extraemos la URL del primer episodio de la lista
        String urlPrimerEpisodio = personaje.get("episode").get(0).asText();

        // Hacemos una llamada a esa URL específica
        return WebClient.create().get()
                .uri(urlPrimerEpisodio)
                .retrieve()
                .bodyToMono(JsonNode.class) // El resultado es el JSON del episodio
                .map(episodio -> {
                    // Creamos un nuevo objeto JSON para combinar la información
                    return com.fasterxml.jackson.databind.node.JsonNodeFactory.instance.objectNode()
                            .put("personaje", nombrePersonaje)
                            .put("primer_episodio", episodio.get("name").asText());
                });
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando la secuencia reactiva...");

        obtenerPersonajes(3) // Obtenemos los 3 primeros personajes
                .flatMap(Six::obtenerInfoPrimerEpisodio) // Para cada personaje, llamamos a la API de episodios
                .subscribe(resultado -> System.out.println("✅ Resultado final: " + resultado));

        // En una aplicación real no usarías Thread.sleep,
        // pero para este ejemplo, evita que el programa termine antes de recibir las respuestas.
        Thread.sleep(5000);
    }
}