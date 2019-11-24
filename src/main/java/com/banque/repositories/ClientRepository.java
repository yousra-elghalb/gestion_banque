package com.banque.repositories;

import com.banque.models.Client;
import com.banque.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findAllByNomContaining(String nom, Pageable pageable);
}
