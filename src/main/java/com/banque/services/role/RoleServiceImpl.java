package com.banque.services.role;

import com.banque.models.Role;
import com.banque.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private Pageable pageable = PageRequest.of(0, 10);

    private final
    RoleRepository compteRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository compteRepository) {
        this.compteRepository = compteRepository;
    }


    public Page<Role> getAll() {
        return compteRepository.findAll(pageable);
    }

    public Optional<Role> findById(Long code) {
        return compteRepository.findById(code);
    }

    public Role save(Role Role) {
        return compteRepository.save(Role);
    }


    public void delete(Long code) {
        compteRepository.deleteById(code);
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Pageable getPageable() {
        return pageable;
    }

}
