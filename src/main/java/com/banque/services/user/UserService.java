package com.banque.services.user;

import com.banque.models.Client;
import com.banque.models.User;
import com.banque.services.Service;

public interface UserService extends Service<User, Long> {
    public void setMotCle(String motCle);

    public String getMotCle();

    User findAllByUsername(String username);
}
