package com.example.reactor_project.handler;

import com.example.reactor_project.entity.Position;
import com.example.reactor_project.repository.PositionRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
public class PositionHandler {

    private final PositionRepository positionRepository;

    public PositionHandler(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(positionRepository.findById(id), Position.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {

        final Mono<Position> position = request.bodyToMono(Position.class);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(position.flatMap(positionRepository::save), Position.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        final String id = request.pathVariable("id");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(positionRepository.deleteById(id), Void.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(positionRepository.findAll(), Position.class);
    }
}
