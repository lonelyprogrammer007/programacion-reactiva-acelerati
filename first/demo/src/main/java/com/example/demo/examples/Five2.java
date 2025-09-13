import reactor.core.publisher.Flux;
import java.time.Duration;

public class Five2 {

    public static Flux<String> obtenerPermisos(String usuario) {
        // Simulamos un retraso de red
        return Flux.just(usuario + ":READ", usuario + ":WRITE")
                .delayElements(Duration.ofMillis(100));
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Usando 'map' para ver los flujos anidados:\n");

        Flux.just("admin", "guest")
                .map(usuario -> obtenerPermisos(usuario)) // Esto nos da un Flux<Flux<String>>
                .subscribe(fluxDePermisos -> {
                    // Para cada Flux interno, lo convertimos a una lista para poder imprimirlo.
                    // collectList() devuelve un Mono<List<String>>, por eso nos suscribimos de nuevo.
                    fluxDePermisos
                            .collectList()
                            .subscribe(listaDePermisos -> {
                                System.out.println("Se recibió una lista de permisos: " + listaDePermisos);
                            });
                });

        // Esperamos un poco para que el proceso asíncrono termine
        Thread.sleep(1000);
    }
}