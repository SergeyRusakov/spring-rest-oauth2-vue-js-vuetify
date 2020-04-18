package ru.sergeyrusakov.testingTask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.sergeyrusakov.testingTask.entities.User;
import ru.sergeyrusakov.testingTask.exceptions.UserNotFoundException;

@Aspect
@Component
public class UserControllerAspect {
    private final Logger logger = LoggerFactory.getLogger("LOGGER");

    @Before("execution(* ru.sergeyrusakov.testingTask.controllers.UserController.delete(..))")
    public void beforeDeleting(JoinPoint joinPoint){
        logger.info("Deleting user with id:"+String.valueOf(joinPoint.getArgs()[0])+" requested");
    }

    @AfterReturning("execution(* ru.sergeyrusakov.testingTask.controllers.UserController.delete(..))")
    public void afterDeleteReturning(JoinPoint joinPoint){
        logger.info("User with id: "+String.valueOf(joinPoint.getArgs()[0])+" deleted");
    }

    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.controllers.UserController.delete(..))",
            throwing = "exception")
    public void afterThrowingDelete(UserNotFoundException exception){
        logger.warn("Delete function has thrown the exception: "+exception.getMessage());
        exception.printStackTrace();
    }

    @Before("execution(* ru.sergeyrusakov.testingTask.controllers.UserController.save(..))")
    public void beforePosting(){
        logger.info("Adding new user requested");
    }

    @AfterReturning(value = "execution(* ru.sergeyrusakov.testingTask.controllers.UserController.save(..))",
            returning = "result")
    public void afterPostReturning(JoinPoint joinPoint,User result){
        logger.info("Successfully added new user with id:"+result.getId());
    }

    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.controllers.UserController.save(..))",
            throwing = "exception")
    public void afterPostThrowing(JoinPoint joinPoint,Exception exception){
        logger.warn("Save user method has thrown the exception: ");
        exception.printStackTrace();
    }

    @Before("execution(* ru.sergeyrusakov.testingTask.controllers.UserController.updateUser(..))")
    public void beforeUpdate(JoinPoint joinPoint){
        User updatingUser = (User)joinPoint.getArgs()[0];
        logger.info("Updating user with id:"+updatingUser.getId()+" requested");
    }

    @AfterReturning(value = "execution(* ru.sergeyrusakov.testingTask.controllers.UserController.updateUser(..))", returning = "result")
    public void afterUpdate(JoinPoint joinPoint,User result){
        logger.info("Saved changes for the user with id:"+result.getId());
    }

    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.controllers.UserController.updateUser(..))",
            throwing = "exception")
    public void afterUpdateThrowing(JoinPoint joinPoint,Exception exception){
        logger.warn("Update user method has thrown the exception: ");
        exception.printStackTrace();
    }

}
