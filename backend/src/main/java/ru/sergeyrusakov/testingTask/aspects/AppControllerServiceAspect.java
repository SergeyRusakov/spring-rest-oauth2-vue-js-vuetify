package ru.sergeyrusakov.testingTask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.sergeyrusakov.testingTask.entities.GitHubUser;

@Aspect
@Component
public class AppControllerServiceAspect {

    private final Logger logger = LoggerFactory.getLogger("LOGGER");

    @Before("execution(* ru.sergeyrusakov.testingTask.service.AppControllerService.updateAuthorities(..))")
    public void beforeUpdatingAuthorities(JoinPoint joinPoint){
        GitHubUser gitHubUser = (GitHubUser)joinPoint.getArgs()[0];
        logger.info("Updating authorities for user with id: "+gitHubUser.getId());
    }
    @AfterReturning(value = "execution(* ru.sergeyrusakov.testingTask.service.AppControllerService.updateAuthorities(..))",
        returning = "result")
    public void afterUpdatingAuthorities(GitHubUser result){
        logger.info("Updated authorities for user with id: "+result.getId());
    }
}
