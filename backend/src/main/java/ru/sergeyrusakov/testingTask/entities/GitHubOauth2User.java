package ru.sergeyrusakov.testingTask.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

//@Data
public class GitHubOauth2User implements OAuth2User {

    private Map<String,Object> attributes;
    private Collection<? extends GrantedAuthority> grantedAuthorities;
    private String name;

    public GitHubOauth2User(Map<String, Object> attributes, String name) {
        this.attributes = attributes;
        this.name = name;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GitHubOauth2User)) return false;
        GitHubOauth2User that = (GitHubOauth2User) o;
        return attributes.equals(that.attributes) &&
                grantedAuthorities.equals(that.grantedAuthorities) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes, grantedAuthorities, name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setGrantedAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public void setName(String name) {
        this.name = name;
    }
}
