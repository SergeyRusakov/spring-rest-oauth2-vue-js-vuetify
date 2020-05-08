package ru.sergeyrusakov.testingTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeyrusakov.testingTask.entities.Position;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position,Integer> {
    Optional<Position> getByName(String name);
}
