package ru.sergeyrusakov.testingTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeyrusakov.testingTask.entities.Position;
import ru.sergeyrusakov.testingTask.exceptions.DataNotFoundException;
import ru.sergeyrusakov.testingTask.repositories.PositionRepository;

import java.util.List;

@Service
public class PositionControllerService {
    @Autowired
    private PositionRepository positionRepository;

    public List<Position> getAll(){
        return positionRepository.findAll();
    }

    public Position getOne(int id){
        return positionRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }
}
