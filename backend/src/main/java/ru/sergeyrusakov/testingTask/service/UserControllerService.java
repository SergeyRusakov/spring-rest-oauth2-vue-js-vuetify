package ru.sergeyrusakov.testingTask.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeyrusakov.testingTask.entities.User;
import ru.sergeyrusakov.testingTask.exceptions.UserNotFoundException;
import ru.sergeyrusakov.testingTask.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserControllerService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User putUser(User newUser){
        return userRepository.findById(newUser.getId()).map(user->{
            BeanUtils.copyProperties(newUser,user,"id");
            user.setTimeLastEdited(LocalDateTime.now());
            return userRepository.save(user);
        }).orElseGet(()->{
            newUser.setCreationDate(LocalDateTime.now());
            newUser.setTimeLastEdited(newUser.getCreationDate());
            return userRepository.save(newUser);
        });
    }

    public User addUser(User user){
        user.setCreationDate(LocalDateTime.now());
        user.setTimeLastEdited(user.getCreationDate());
        userRepository.save(user);
        return user;
    }

    public void deleteUser(int id) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
        userRepository.deleteById(id);
    }

}
