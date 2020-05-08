package ru.sergeyrusakov.testingTask.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import ru.sergeyrusakov.testingTask.entities.Organization;
import ru.sergeyrusakov.testingTask.exceptions.DataNotFoundException;
import ru.sergeyrusakov.testingTask.repositories.OrganizationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrganizationControllerService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Organization> getAll(){
        return organizationRepository.findAll();
    }

    public Organization getOne(int id){
        return organizationRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    //Сделать тест
    public void delete(int id){
        organizationRepository.findById(id).orElseThrow(DataNotFoundException::new);
        organizationRepository.deleteById(id);
    }

    public Organization update(Organization updatingOrganization){
        return organizationRepository.findById(updatingOrganization.getId()).map(organization -> {
            BeanUtils.copyProperties(updatingOrganization,organization);
            updatingOrganization.setTimeUpdated(LocalDateTime.now());
            return organizationRepository.save(updatingOrganization);
        }).orElseGet(()->{
            updatingOrganization.setTimeAdded(LocalDateTime.now());
            updatingOrganization.setTimeUpdated(LocalDateTime.now());
            return organizationRepository.save(updatingOrganization);
        });
    }

    public Organization save(Organization organization){
        organization.setTimeAdded(LocalDateTime.now());
        organization.setTimeUpdated(LocalDateTime.now());
        return organizationRepository.save(organization);
    }
}
