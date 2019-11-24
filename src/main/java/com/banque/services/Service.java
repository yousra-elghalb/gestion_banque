package com.banque.services;

import com.banque.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface Service<T, C> {

    Page<T> getAll();

    Optional<T> findById(C code);

    T save(T data);

    void delete(C code);

    void setPageable(Pageable pageable);

    Pageable getPageable();
}
