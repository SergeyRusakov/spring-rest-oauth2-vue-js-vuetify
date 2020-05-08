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
import ru.sergeyrusakov.testingTask.entities.Organization;

@Aspect
@Component
public class EmployeeControllerServiceAspect {

    private final Logger logger = LoggerFactory.getLogger("LOGGER");

    @Before("execution(* ru.sergeyrusakov.testingTask.service.EmployeeControllerService.delete(..))")
    public void beforeDeletingEmployee(JoinPoint joinPoint){
        logger.info("Deleting employee with id:"+ joinPoint.getArgs()[0]);
    }
    @AfterReturning("execution(* ru.sergeyrusakov.testingTask.service.EmployeeControllerService.delete(..))")
    public void afterDeletingEmployee(JoinPoint joinPoint){
        logger.info("Employee deleted. id: "+joinPoint.getArgs()[0]);
    }
    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.service.EmployeeControllerService.delete(..))",
            throwing = "e")
    public void afterThrowingOnDelete(JoinPoint joinPoint, Exception e){
        logger.warn("Cannot delete employee with id: "+joinPoint.getArgs()+
                ". Deleting operation has thrown the exception: "+e.getMessage());
    }
    @Before("execution(* ru.sergeyrusakov.testingTask.service.EmployeeControllerService.update(..))")
    public void beforeUpdatingEmployee(JoinPoint joinPoint){
        Employee employee = (Employee) joinPoint.getArgs()[0];
        logger.info("Updating employee with id: "+employee.getId());
    }
    @AfterReturning("execution(* ru.sergeyrusakov.testingTask.service.EmployeeControllerService.update(..))")
    public void afterUpdatingEmployee(JoinPoint joinPoint){
        Employee employee = (Employee) joinPoint.getArgs()[0];
        logger.info("Employee updated. id: "+employee.getId());
    }
    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.service.EmployeeControllerService.update(..))",
            throwing = "e")
    public void afterThrowingOnUpdating(JoinPoint joinPoint, Exception e){
        Employee employee = (Employee) joinPoint.getArgs()[0];
        logger.warn("Cannot update employee with id: "+employee.getId()+
                ". Updating operation has thrown the exception: "+e.getMessage());
    }
    @Before("execution(* ru.sergeyrusakov.testingTask.service.EmployeeControllerService.save(..))")
    public void beforeAddingOrganization(JoinPoint joinPoint){
        Employee employee = (Employee) joinPoint.getArgs()[0];
        logger.info("Adding employee with name: "+employee.getName());
    }
    @AfterReturning(value = "execution(* ru.sergeyrusakov.testingTask.service.EmployeeControllerService.save(..))",
            returning = "result")
    public void afterSavingOrganization(Employee result){
        logger.info("Added employee with id: "+result.getId());
    }
}
