package ru.sergeyrusakov.testingTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sergeyrusakov.testingTask.entities.GitHubUser;
import ru.sergeyrusakov.testingTask.service.AppControllerService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class AppController {

    @Autowired
    private AppControllerService appControllerService;

    @GetMapping("/currentuser")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Principal principal (Principal principal){
        return principal;
    }

    @GetMapping("/redirect")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:4000");
    }

    @GetMapping("/test")
    public String test(){
        return "response";
    }

    @GetMapping("/oauth2Users")
    public List<GitHubUser> getOauth2Users(){
        return appControllerService.getAllOauthUsers();
    }

    @PutMapping("/oauth2Users")
    public GitHubUser updateAuthorities(@RequestBody GitHubUser gitHubUser){
        return appControllerService.updateAuthorities(gitHubUser);
    }

}
