package ru.sergeyrusakov.testingTask.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sergeyrusakov.testingTask.entities.User;
import ru.sergeyrusakov.testingTask.exceptions.InvalidUserDataException;
import ru.sergeyrusakov.testingTask.exceptions.UserNotFoundException;
import ru.sergeyrusakov.testingTask.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    //returns all of the users from database
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public List<User> getAll(){
        return repository.findAll();
    }

    //returns one user by id
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public User getById(@PathVariable int id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    //saves changes to the user
    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public User updateUser(@RequestBody User newUser) throws InvalidUserDataException {
        if(!DataValidationUtil.userDataIsValid(newUser)) throw new InvalidUserDataException();
        return repository.findById(newUser.getId()).map(user->{
                    BeanUtils.copyProperties(newUser,user,"id");
                    user.setTimeLastEdited(LocalDateTime.now());
                    return repository.save(user);
                }).orElseGet(()->{
                    newUser.setCreationDate(LocalDateTime.now());
                    newUser.setTimeLastEdited(newUser.getCreationDate());
                    return repository.save(newUser);
        });
    }

    //Adds a new user to database and returns it
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public User save(@RequestBody User user) throws InvalidUserDataException {
        if(!DataValidationUtil.userDataIsValid(user)) throw new InvalidUserDataException();
        user.setCreationDate(LocalDateTime.now());
        user.setTimeLastEdited(user.getCreationDate());
        repository.save(user);
        return user;
    }

    //Deletes user by id
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void delete(@PathVariable int id) throws UserNotFoundException {
        repository.findById(id).orElseThrow(()->new UserNotFoundException("User "+id+" not found"));
        repository.deleteById(id);
    }

}
