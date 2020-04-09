package ru.sergeyrusakov.testingTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeyrusakov.testingTask.entities.GitHubUser;

@Repository
public interface GitHubUserRepository extends JpaRepository<GitHubUser,Integer> {

}
