package ru.sergeyrusakov.testingTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ru.sergeyrusakov.testingTask.repositories")
@EnableAspectJAutoProxy
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}

//TODO переделать юзера в работника
//TODO сделать незащищенный профиль для тестирования
//TODO добавить 2 сущности (организации, работники)
//TODO сделать маппинги между ними

