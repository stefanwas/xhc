package com.stefan.xhc.user;

import java.util.Arrays;
import java.util.List;

public class User {

    private String name;
    private String password;
    private List<String> authorities;

    public User(String name, String password, String... authorities) {
        this.name = name;
        this.password = password;
        this.authorities = Arrays.asList(authorities);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
