package ru.sergeyrusakov.testingTask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sergeyrusakov.testingTask.entities.GitHubUser;
import ru.sergeyrusakov.testingTask.exceptions.EmplyeeNotFoundException;
import ru.sergeyrusakov.testingTask.repositories.GitHubUserRepository;

@Aspect
@Component
public class AppControllerAspect {
    private final Logger logger = LoggerFactory.getLogger("LOGGER");
    @Autowired
    private GitHubUserRepository repository;
    @Before("execution(* ru.sergeyrusakov.testingTask.controllers.AppController.updateAuthorities(..))")
    public void beforeUpdatingAuthorities(JoinPoint joinPoint) throws EmplyeeNotFoundException {
        if(joinPoint.getArgs()[0] instanceof GitHubUser){
            GitHubUser user = (GitHubUser)joinPoint.getArgs()[0];
            GitHubUser gitHubUser = repository.findById(user.getId()).orElseThrow(EmplyeeNotFoundException::new);
            logger.info("Changing authorities of user "
                    +gitHubUser.getLogin()+" id: "+gitHubUser.getId()+" current role: "+gitHubUser.getRole());
        }
    }
    @AfterReturning(value = "execution(* ru.sergeyrusakov.testingTask.controllers.AppController.updateAuthorities(..))"
            ,returning = "gitHubUser")
    public void afterReturningAuthorities(GitHubUser gitHubUser){
        logger.info("Authorities of user "
                +gitHubUser.getLogin()+" id: "
                +gitHubUser.getId()+" changed. current role : "+gitHubUser.getRole());
    }
}
