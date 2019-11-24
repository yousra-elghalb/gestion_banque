package com.banque.services.compte;

import com.banque.configurations.security.Outils;
import com.banque.models.Client;
import com.banque.models.Role;
import com.banque.models.compte.Compte;
import com.banque.repositories.ClientRepository;
import com.banque.repositories.CompteRepository;
import com.banque.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {
    private Pageable pageable = PageRequest.of(0, 10);
    private String motCle = "";
    private final
    CompteRepository compteRepository;
    private final
    ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final Outils outils;

    @Autowired
    public CompteServiceImpl(CompteRepository compteRepository, ClientRepository clientRepository, RoleRepository roleRepository, Outils outils) {
        this.compteRepository = compteRepository;
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.outils = outils;
    }

    @Override
    public Collection<Compte> getByClientCode(long code) {
        return compteRepository.findAllByClientCode(code);
    }

    @Override
    public Page<Compte> getByUserId(long id) {
        return compteRepository.findAllByClientUserId(id, pageable);
    }

    public Page<Compte> getAll() {
        return compteRepository.findAllByCodeContaining(motCle, pageable);
    }

    public Optional<Compte> findById(String code) {
        return compteRepository.findById(code);
    }

    public Compte save(Compte compte) {
        if (compte.getClient().getCode() != 0) {
            Client client = clientRepository.findById(compte.getClient().getCode()).orElse(null);
            compte.setClient(client);
        } else {
            Role role = roleRepository.findByName("CLIENT");
            compte.getClient().getUser().setRole(role);
            compte.getClient().getUser().setPassword(outils.generatePassword(8));
        }
        compte.setDateCreation(new Date());
        return compteRepository.save(compte);
    }

//    public Compte saveClient(CompteCourant compte) {
//        compte.setDateCreation(new Date());
//        return compteRepository.save(compte);
//    }


    public void delete(String code) {
        compteRepository.deleteById(code);
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setMotCle(String motCle) {
        this.motCle = motCle;
    }

    public String getMotCle() {
        return motCle;
    }
}
