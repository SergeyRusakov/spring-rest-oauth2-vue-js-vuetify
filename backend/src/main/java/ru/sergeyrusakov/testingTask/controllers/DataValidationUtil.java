package ru.sergeyrusakov.testingTask.controllers;


import ru.sergeyrusakov.testingTask.entities.User;

import java.time.LocalDate;

public class DataValidationUtil {

    public static boolean userDataIsValid(User user){
        return nameIsValid(user.getName())
                &&nameIsValid(user.getSurname())
                &&emailIsValid(user.getEmail())
                &&birthDateIsValid(user.getBirthDate());
    }

    public static boolean nameIsValid(String name){
        return name.matches("^[a-zA-Zа-яА-Я-'/./ ]+$")&&name.length()<16&&name!=null;
    }

    public static boolean emailIsValid(String email){
        return email.matches("[a-zA-Z_-[0-9]]+@[a-zA-Z_-[0-9]]+\\..+")&&email!=null&&!email.isEmpty();
    }

    public static boolean birthDateIsValid(LocalDate localDate){
        return localDate.isAfter(LocalDate.parse("1900-01-01"))&&localDate.isBefore(LocalDate.now());
    }

}
