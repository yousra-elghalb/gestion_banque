package com.banque.repositories;

import com.banque.models.compte.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CompteRepository extends JpaRepository<Compte, String> {


    Page<Compte> findAllByCodeContaining(String code, Pageable pageable);

    Page<Compte> findAllByClientUserId(long id, Pageable pageable);

    Compte findByCode(String code);

    Collection<Compte> findAllByClientCode(long code);
}
