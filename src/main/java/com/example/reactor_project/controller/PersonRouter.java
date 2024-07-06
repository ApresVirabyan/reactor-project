package com.example.reactor_project.controller;

import com.example.reactor_project.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PersonRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PersonHandler personHandler){
        return RouterFunctions
                .route(GET("/getAllPersons").and(accept(MediaType.APPLICATION_JSON)), personHandler::getAll)
                .andRoute(GET("/person/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), personHandler::findById)
                .andRoute(POST("/createPerson").and(accept(MediaType.APPLICATION_JSON)), personHandler::save)
                .andRoute(DELETE("/deletePerson/{id}").and(accept(MediaType.APPLICATION_JSON)), personHandler::delete);
    }
}
