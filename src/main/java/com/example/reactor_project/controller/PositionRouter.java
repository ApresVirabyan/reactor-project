package com.example.reactor_project.controller;

import com.example.reactor_project.handler.PositionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PositionRouter {

    public RouterFunction<ServerResponse> positionRouter(PositionHandler positionHandler) {
        return RouterFunctions
                .route(GET("/getAllPositions").and(accept(MediaType.APPLICATION_JSON)), positionHandler::findAll)
                .andRoute(GET("/position/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), positionHandler::findById)
                .andRoute(POST("/createPosition").and(accept(MediaType.APPLICATION_JSON)), positionHandler::save)
                .andRoute(DELETE("/deletePosition/{id}").and(accept(MediaType.APPLICATION_JSON)), positionHandler::delete);
    }
}
