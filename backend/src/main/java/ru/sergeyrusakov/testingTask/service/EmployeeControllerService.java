package ru.sergeyrusakov.testingTask.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeyrusakov.testingTask.entities.Employee;
import ru.sergeyrusakov.testingTask.entities.Organization;
import ru.sergeyrusakov.testingTask.exceptions.DataNotFoundException;
import ru.sergeyrusakov.testingTask.exceptions.InvalidDataException;
import ru.sergeyrusakov.testingTask.repositories.EmployeeRepository;
import ru.sergeyrusakov.testingTask.repositories.OrganizationRepository;
import ru.sergeyrusakov.testingTask.repositories.PositionRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeControllerService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionRepository positionRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployee(int id) throws DataNotFoundException {
        return employeeRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public Employee update(Employee newEmployee){

        return employeeRepository.findById(newEmployee.getId()).map(employee->{
            BeanUtils.copyProperties(newEmployee,employee,"id");
            employee.setTimeUpdated(LocalDateTime.now());
            return employeeRepository.save(employee);
        }).orElseGet(()->{
            newEmployee.setTimeAdded(LocalDateTime.now());
            newEmployee.setTimeUpdated(newEmployee.getTimeAdded());
            return employeeRepository.save(newEmployee);
        });
    }

    public Employee save(Employee employee){
        if(positionRepository.getByName(employee.getPosition().getName()).isEmpty()){
            positionRepository.save(employee.getPosition());
        }
        employee.setTimeAdded(LocalDateTime.now());
        employee.setTimeUpdated(employee.getTimeAdded());
        employeeRepository.save(employee);
        return employee;
    }

    public void delete(int id) throws DataNotFoundException {
        employeeRepository.findById(id).orElseThrow(DataNotFoundException::new);
        employeeRepository.deleteById(id);
    }

}
