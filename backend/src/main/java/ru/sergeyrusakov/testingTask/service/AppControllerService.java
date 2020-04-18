package ru.sergeyrusakov.testingTask.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import ru.sergeyrusakov.testingTask.entities.GitHubOauth2User;
import ru.sergeyrusakov.testingTask.entities.GitHubUser;
import ru.sergeyrusakov.testingTask.repositories.GitHubUserRepository;

import java.util.List;

@Service
public class AppControllerService {
    @Autowired
    private GitHubUserRepository gitHubUserRepository;
    @Autowired
    private SessionRegistry sessionRegistry;

    public List<GitHubUser> getAllOauthUsers(){
        return gitHubUserRepository.findAll();
    }

    public GitHubUser updateAuthorities(GitHubUser gitHubUser){
        sessionRegistry.getAllPrincipals().forEach((principal)->{
            if(principal instanceof GitHubOauth2User){
                if(((GitHubOauth2User) principal).getAttributes().get("id").equals(gitHubUser.getId())){
                    sessionRegistry.getAllSessions(principal,true).forEach((session)->{
                        session.expireNow();
                    });
                }
            }
        });
        return gitHubUserRepository.findById(gitHubUser.getId()).map(user->{
            BeanUtils.copyProperties(gitHubUser,user,"id");
            return gitHubUserRepository.save(user);
        }).orElseGet(()-> gitHubUserRepository.save(gitHubUser));
    }
}
