package ru.sergeyrusakov.testingTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sergeyrusakov.testingTask.entities.Employee;
import ru.sergeyrusakov.testingTask.exceptions.InvalidEmployeeDataException;
import ru.sergeyrusakov.testingTask.exceptions.EmplyeeNotFoundException;
import ru.sergeyrusakov.testingTask.service.EmployeeControllerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeControllerService employeeControllerService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public List<Employee> getAll(){
        return employeeControllerService.getAllEmployees();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Employee getById(@PathVariable int id) throws EmplyeeNotFoundException {
        return employeeControllerService.getEmployee(id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void delete(@PathVariable int id) throws EmplyeeNotFoundException {
        employeeControllerService.deleteEmployee(id);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Employee update(@RequestBody @Valid Employee newEmployee,
                                   BindingResult bindingResult) throws InvalidEmployeeDataException {
        if(bindingResult.hasErrors()) throw new InvalidEmployeeDataException();
        return employeeControllerService.putEmployee(newEmployee);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Employee save(@RequestBody @Valid Employee employee,
                         BindingResult bindingResult) throws InvalidEmployeeDataException {
        if(bindingResult.hasErrors()) throw new InvalidEmployeeDataException();
        return employeeControllerService.addEmployee(employee);
    }
}
