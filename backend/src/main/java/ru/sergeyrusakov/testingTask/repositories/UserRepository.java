package ru.sergeyrusakov.testingTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeyrusakov.testingTask.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
