package com.example.reactor_project.handler;

import com.example.reactor_project.entity.Person;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import com.example.reactor_project.service.PersonService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class PersonHandler {

    private final PersonService personService;

    public PersonHandler(PersonService personService) {
        this.personService = personService;
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(personService.getById(id), Person.class);
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(personService.getAll(), Person.class);
    }

    public Mono<ServerResponse> save(ServerRequest request){
        final Mono<Person> person = request.bodyToMono(Person.class);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(person.flatMap(personService::save), Person.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        final String id = request.pathVariable("id");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.delete(id), Void.class);
    }

}
