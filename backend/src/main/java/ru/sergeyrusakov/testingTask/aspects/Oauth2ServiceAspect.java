package ru.sergeyrusakov.testingTask.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.sergeyrusakov.testingTask.entities.GitHubOauth2User;

@Aspect
@Component
public class Oauth2ServiceAspect {
    private final Logger logger = LoggerFactory.getLogger("LOGGER");
    @Before("execution(* ru.sergeyrusakov.testingTask.service.GitHubUserService.loadUser(..))")
    public void beforeUserLoading() {
        logger.info("GitHub oauth2user is logging");
    }
    @AfterReturning(value = "execution(* ru.sergeyrusakov.testingTask.service.GitHubUserService.loadUser(..))",
    returning = "oauth2User")
    public void afterUserLoading(GitHubOauth2User oauth2User) {
        logger.info("User "+oauth2User.getAttributes().get("login")+" logged in with GitHub");
    }
    @AfterThrowing(value = "execution(* ru.sergeyrusakov.testingTask.service.GitHubUserService.loadUser(..))",
            throwing = "exception")
    public void afterUserThrowing(Exception exception) {
        logger.warn("GitHubUserService has thrown the exception. Cannot load user");
        exception.printStackTrace();
    }

}
