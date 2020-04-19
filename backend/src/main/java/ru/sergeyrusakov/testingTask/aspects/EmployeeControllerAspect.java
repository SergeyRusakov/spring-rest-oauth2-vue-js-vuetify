package ru.sergeyrusakov.testingTask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.sergeyrusakov.testingTask.entities.Employee;
import ru.sergeyrusakov.testingTask.exceptions.EmplyeeNotFoundException;

@Aspect
@Component
public class EmployeeControllerAspect {
    private final Logger logger = LoggerFactory.getLogger("LOGGER");

    @Before("execution(* ru.sergeyrusakov.testingTask.controllers.EmployeeController.delete(..))")
    public void beforeDeleting(JoinPoint joinPoint){
        logger.info("Deleting employee with id:"+ joinPoint.getArgs()[0] +" requested");
    }

    @AfterReturning("execution(* ru.sergeyrusakov.testingTask.controllers.EmployeeController.delete(..))")
    public void afterDeleteReturning(JoinPoint joinPoint){
        logger.info("Employee with id: "+ joinPoint.getArgs()[0] +" deleted");
    }

    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.controllers.EmployeeController.delete(..))",
            throwing = "exception")
    public void afterThrowingDelete(EmplyeeNotFoundException exception){
        logger.warn("Delete function has thrown the exception: "+exception.getMessage());
        exception.printStackTrace();
    }

    @Before("execution(* ru.sergeyrusakov.testingTask.controllers.EmployeeController.save(..))")
    public void beforePosting(){
        logger.info("Adding new employee requested");
    }

    @AfterReturning(value = "execution(* ru.sergeyrusakov.testingTask.controllers.EmployeeController.save(..))",
            returning = "result")
    public void afterPostReturning(Employee result){
        logger.info("Successfully added new employee with id:"+result.getId());
    }

    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.controllers.EmployeeController.save(..))",
            throwing = "exception")
    public void afterPostThrowing(Exception exception){
        logger.warn("Save employee method has thrown the exception: ");
        exception.printStackTrace();
    }

    @Before("execution(* ru.sergeyrusakov.testingTask.controllers.EmployeeController.update(..))")
    public void beforeUpdate(JoinPoint joinPoint){
        Employee updatingEmployee = (Employee)joinPoint.getArgs()[0];
        logger.info("Updating employee with id:"+ updatingEmployee.getId()+" requested");
    }

    @AfterReturning(value = "execution(* ru.sergeyrusakov.testingTask.controllers.EmployeeController.update(..))", returning = "result")
    public void afterUpdate(Employee result){
        logger.info("Saved changes for the employee with id:"+result.getId());
    }

    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.controllers.EmployeeController.update(..))",
            throwing = "exception")
    public void afterUpdateThrowing(Exception exception){
        logger.warn("Update employee method has thrown the exception: ");
        exception.printStackTrace();
    }

}
