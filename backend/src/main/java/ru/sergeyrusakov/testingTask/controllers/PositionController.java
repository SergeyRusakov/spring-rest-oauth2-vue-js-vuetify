package ru.sergeyrusakov.testingTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sergeyrusakov.testingTask.entities.Position;
import ru.sergeyrusakov.testingTask.service.PositionControllerService;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    @Autowired
    private PositionControllerService positionControllerService;

    @GetMapping
    public List<Position> getAll(){
        return positionControllerService.getAll();
    }

    @GetMapping("{id}")
    public Position getOne(@PathVariable int id){
        return positionControllerService.getOne(id);
    }
}
