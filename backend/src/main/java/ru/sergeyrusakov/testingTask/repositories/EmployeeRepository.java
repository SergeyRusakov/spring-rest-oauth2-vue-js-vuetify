package ru.sergeyrusakov.testingTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeyrusakov.testingTask.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
