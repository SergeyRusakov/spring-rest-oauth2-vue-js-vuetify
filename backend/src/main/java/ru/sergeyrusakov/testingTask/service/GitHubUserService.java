package ru.sergeyrusakov.testingTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.sergeyrusakov.testingTask.entities.GitHubOauth2User;
import ru.sergeyrusakov.testingTask.entities.GitHubUser;
import ru.sergeyrusakov.testingTask.repositories.GitHubUserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class GitHubUserService extends DefaultOAuth2UserService {
    @Autowired
    private GitHubUserRepository repository;
    //Saves principal to database before loading it
    @Override
    public GitHubOauth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User defaultOAuth2User = super.loadUser(userRequest);
        GitHubOauth2User gitHubOauth2User =
                new GitHubOauth2User(defaultOAuth2User.getAttributes(),defaultOAuth2User.getName());

        Map<String,Object> attributes = gitHubOauth2User.getAttributes();
        Collection<? extends GrantedAuthority> authorities = defaultOAuth2User.getAuthorities();

        GitHubUser gitHubUser = repository.findById((Integer)attributes.get("id"))
                .orElseGet(()->{
                    GitHubUser newUser = new GitHubUser();
                    newUser.setId((Integer)attributes.get("id"));
                    newUser.setLogin((String)attributes.get("login"));
                    newUser.setName((String)attributes.get("name"));
                    newUser.setRole(authorities.toArray()[0].toString());
                    return newUser;
                });

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        authorities.forEach((authority)->list.add(new SimpleGrantedAuthority(authority.getAuthority())));
        list.set(0,(new SimpleGrantedAuthority(gitHubUser.getRole())));

        gitHubOauth2User.setGrantedAuthorities(list);
        gitHubUser.setLastVisit(LocalDateTime.now());

        repository.save(gitHubUser);
        return gitHubOauth2User;
    }
}
