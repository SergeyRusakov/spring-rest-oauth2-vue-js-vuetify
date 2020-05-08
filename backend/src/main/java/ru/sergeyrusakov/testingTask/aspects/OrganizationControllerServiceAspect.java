package ru.sergeyrusakov.testingTask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.sergeyrusakov.testingTask.entities.Organization;

@Aspect
@Component
public class OrganizationControllerServiceAspect {

    private final Logger logger = LoggerFactory.getLogger("LOGGER");

    @Before("execution(* ru.sergeyrusakov.testingTask.service.OrganizationControllerService.delete(..))")
    public void beforeDeletingOrganization(JoinPoint joinPoint){
        logger.info("Deleting organization with id:"+ joinPoint.getArgs()[0]);
    }
    @AfterReturning("execution(* ru.sergeyrusakov.testingTask.service.OrganizationControllerService.delete(..))")
    public void afterDeletingOrganization(JoinPoint joinPoint){
        logger.info("Organization deleted. id: "+joinPoint.getArgs()[0]);
    }
    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.service.OrganizationControllerService.delete(..))",
        throwing = "e")
    public void afterThrowingOnDelete(JoinPoint joinPoint,Exception e){
        logger.warn("Cannot delete organization with id: "+joinPoint.getArgs()+
                ". Deleting operation has thrown the exception: "+e.getMessage());
    }
    @Before("execution(* ru.sergeyrusakov.testingTask.service.OrganizationControllerService.update(..))")
    public void beforeUpdatingOrganization(JoinPoint joinPoint){
        Organization organization = (Organization)joinPoint.getArgs()[0];
        logger.info("Updating organization with id: "+organization.getId());
    }
    @AfterReturning("execution(* ru.sergeyrusakov.testingTask.service.OrganizationControllerService.update(..))")
    public void afterUpdatingOrganization(JoinPoint joinPoint){
        Organization organization = (Organization)joinPoint.getArgs()[0];
        logger.info("Organization updated. id: "+organization.getId());
    }
    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.service.OrganizationControllerService.update(..))",
    throwing = "e")
    public void afterThrowingOnUpdating(JoinPoint joinPoint,Exception e){
        Organization organization = (Organization)joinPoint.getArgs()[0];
        logger.warn("Cannot update organization with id: "+organization.getId()+
                ". Updating operation has thrown the exception: "+e.getMessage());
    }
    @Before("execution(* ru.sergeyrusakov.testingTask.service.OrganizationControllerService.save(..))")
    public void beforeAddingOrganization(JoinPoint joinPoint){
        Organization organization = (Organization)joinPoint.getArgs()[0];
        logger.info("Adding organization with name: "+organization.getName());
    }
    @AfterReturning(value = "execution(* ru.sergeyrusakov.testingTask.service.OrganizationControllerService.save(..))",
        returning = "result")
    public void afterSavingOrganization(Organization result){
        logger.info("Added organization with id: "+result.getId());
    }
}
