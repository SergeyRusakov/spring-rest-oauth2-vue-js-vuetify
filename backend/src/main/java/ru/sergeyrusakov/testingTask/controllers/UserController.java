package ru.sergeyrusakov.testingTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sergeyrusakov.testingTask.entities.User;
import ru.sergeyrusakov.testingTask.exceptions.InvalidUserDataException;
import ru.sergeyrusakov.testingTask.exceptions.UserNotFoundException;
import ru.sergeyrusakov.testingTask.service.UserControllerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserControllerService userControllerService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public List<User> getAll(){
        return userControllerService.getAllUsers();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public User getById(@PathVariable int id) throws UserNotFoundException {
        return userControllerService.getUser(id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void delete(@PathVariable int id) throws UserNotFoundException {
        userControllerService.deleteUser(id);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public User updateUser(@RequestBody @Valid User newUser,
                           BindingResult bindingResult) throws InvalidUserDataException {
        if(bindingResult.hasErrors()) throw new InvalidUserDataException();
        return userControllerService.putUser(newUser);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public User save(@RequestBody @Valid User user,
                     BindingResult bindingResult) throws InvalidUserDataException {
        if(bindingResult.hasErrors()) throw new InvalidUserDataException();
        return userControllerService.addUser(user);
    }
}
