package ru.sergeyrusakov.testingTask.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeyrusakov.testingTask.entities.Employee;
import ru.sergeyrusakov.testingTask.exceptions.EmplyeeNotFoundException;
import ru.sergeyrusakov.testingTask.repositories.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeControllerService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployee(int id) throws EmplyeeNotFoundException {
        return employeeRepository.findById(id).orElseThrow(EmplyeeNotFoundException::new);
    }

    public Employee putEmployee(Employee newEmployee){
        return employeeRepository.findById(newEmployee.getId()).map(employee->{
            BeanUtils.copyProperties(newEmployee,employee,"id");
            employee.setTimeLastEdited(LocalDateTime.now());
            return employeeRepository.save(employee);
        }).orElseGet(()->{
            newEmployee.setCreationDate(LocalDateTime.now());
            newEmployee.setTimeLastEdited(newEmployee.getCreationDate());
            return employeeRepository.save(newEmployee);
        });
    }

    public Employee addEmployee(Employee employee){
        employee.setCreationDate(LocalDateTime.now());
        employee.setTimeLastEdited(employee.getCreationDate());
        employeeRepository.save(employee);
        return employee;
    }

    public void deleteEmployee(int id) throws EmplyeeNotFoundException {
        employeeRepository.findById(id).orElseThrow(EmplyeeNotFoundException::new);
        employeeRepository.deleteById(id);
    }

}
