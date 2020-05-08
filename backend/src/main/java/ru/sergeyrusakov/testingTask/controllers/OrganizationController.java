package ru.sergeyrusakov.testingTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sergeyrusakov.testingTask.entities.Organization;
import ru.sergeyrusakov.testingTask.exceptions.InvalidDataException;
import ru.sergeyrusakov.testingTask.service.OrganizationControllerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationControllerService organizationControllerService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public List<Organization> getAll(){
        return organizationControllerService.getAll();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Organization getOne(@PathVariable int id){
        return organizationControllerService.getOne(id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void delete(@PathVariable int id){
        organizationControllerService.delete(id);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Organization update(@RequestBody @Valid Organization organization,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new InvalidDataException();
        return organizationControllerService.update(organization);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Organization save(@RequestBody @Valid Organization organization,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new InvalidDataException();
        return organizationControllerService.save(organization);
    }
}
