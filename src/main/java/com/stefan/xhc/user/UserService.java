package com.stefan.xhc.user;

import org.springframework.stereotype.Component;

@Component
public class UserService {

    public User findByLogin(String name) {
        // for test always return the same user
        return new User("wojtek", "abc123", "admin");
    }
}
