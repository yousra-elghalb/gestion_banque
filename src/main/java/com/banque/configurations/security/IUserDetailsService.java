package com.banque.configurations.security;

import com.banque.models.User;
import com.banque.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class IUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public IUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User users = userService.findAllByUsername(s);
        if (users == null) throw new UsernameNotFoundException("not exist");





        return users;
    }
}
