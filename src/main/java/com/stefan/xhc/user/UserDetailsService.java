package com.stefan.xhc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userService.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        }
//        else if (!user.getEnabled()) {
//            throw new UserNotEnabledException("User " + login + " was not enabled");
//        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        for (String authority : user.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
            grantedAuthorities.add(grantedAuthority);
        }

        return new org.springframework.security.core.userdetails.User(login, user.getPassword(), grantedAuthorities);
    }
}
