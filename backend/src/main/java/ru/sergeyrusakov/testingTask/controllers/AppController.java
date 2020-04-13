package ru.sergeyrusakov.testingTask.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sergeyrusakov.testingTask.entities.GitHubOauth2User;
import ru.sergeyrusakov.testingTask.entities.GitHubUser;
import ru.sergeyrusakov.testingTask.repositories.GitHubUserRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;


@RestController
public class AppController {
    @Autowired
    private GitHubUserRepository repository;
    @Autowired
    private SessionRegistry sessionRegistry;

    //Returns principal
    @GetMapping("/currentuser")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Principal principal (Principal principal){
        return principal;
    }

    //Redirects back to frontend after oauth
    @GetMapping("/redirect")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:4000");
    }

    //For test
    @GetMapping("/test")
    public String test(){
        return "response";
    }

    //Returns list of all principals, who has been authenticated with GitHub
    @GetMapping("/oauth2Users")
    public List<GitHubUser> getOauth2Users(){
        return repository.findAll();
    }

    //Updates principal status
    @PutMapping("/oauth2Users")
    public GitHubUser updateAuthorities(@RequestBody GitHubUser gitHubUser){
        sessionRegistry.getAllPrincipals().forEach((principal)->{
            if(principal instanceof GitHubOauth2User){
                if(((GitHubOauth2User) principal).getAttributes().get("id").equals(gitHubUser.getId())){
                    sessionRegistry.getAllSessions(principal,true).forEach((session)->{
                        session.expireNow();
                    });
                }
            }
        });
        return repository.findById(gitHubUser.getId()).map(user->{
            BeanUtils.copyProperties(gitHubUser,user,"id");
            return repository.save(user);
        }).orElseGet(()-> repository.save(gitHubUser));
    }

}
