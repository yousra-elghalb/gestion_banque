package com.banque.repositories;

import com.banque.models.Client;
import com.banque.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByUsernameContaining(String username, Pageable pageable);

    User findAllByUsername(String username);
}
