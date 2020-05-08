package ru.sergeyrusakov.testingTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeyrusakov.testingTask.entities.Organization;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Integer> {
    Optional<Organization> getByName(String name);
}
