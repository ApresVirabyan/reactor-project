package com.example.reactor_project.repository;

import com.example.reactor_project.entity.Position;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends ReactiveMongoRepository<Position, String> {
}
