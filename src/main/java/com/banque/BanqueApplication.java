package com.banque;

import com.banque.models.Role;
import com.banque.models.User;
import com.banque.services.role.RoleServiceImpl;
import com.banque.services.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class BanqueApplication implements CommandLineRunner {


    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RoleServiceImpl roleServiceImpl;


    public static void main(String[] args) {
        SpringApplication.run(BanqueApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    /*    List<Role> roles = roleServiceImpl.getAll().getContent();
        Role radmin = roles.get(0);
        Role rclient = roles.get(1);
        Role ruser = roles.get(2);
        User user = new User("u", "0");
        user.setRole(ruser);
        User admin = new User("a", "0");
        admin.setRole(radmin);
        User client = new User("c", "0");
        client.setRole(rclient);

        userServiceImpl.save(user);
        userServiceImpl.save(admin);
        userServiceImpl.save(client);*/
    }
}
