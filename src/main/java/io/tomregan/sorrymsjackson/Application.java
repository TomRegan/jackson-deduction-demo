package io.tomregan.sorrymsjackson;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION;
import static io.tomregan.sorrymsjackson.Application.AnimalSpottedEvent.*;
import static io.tomregan.sorrymsjackson.Application.AnimalSpottedEvent.Animal.*;

import java.time.Instant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.tomregan.sorrymsjackson.Application.AnimalSpottedEvent.Animal.IntergalacticAnimal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RestController
    static final class AnimalsController {

        @PostMapping(
                value = "/animals",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<AnimalSpottedEvent<?>> createAnimal(@Valid @RequestBody AnimalSpottedEvent<?> event) {
            // send the event back to the client, as a little treat
            return ResponseEntity.ok(event);
        }
    }

    record AnimalSpottedEvent<A extends Animal>(
            @NotNull
            Instant created,
            @NotNull
            @JsonTypeInfo(use = DEDUCTION)
            @JsonSubTypes({
                    @Type(IntergalacticAnimal.class),
                    @Type(ProvincialAnimal.class)})
            A animal) {
        sealed interface Animal {
            record IntergalacticAnimal(String galaxy, String name) implements Animal {
            }

            record ProvincialAnimal(String province, String name) implements Animal {
            }
        }
    }
}
