package com.banque.services.client;

import com.banque.configurations.security.Outils;
import com.banque.models.Client;
import com.banque.models.Role;
import com.banque.models.forms.ClientForm;
import com.banque.repositories.ClientRepository;
import com.banque.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private Pageable pageable = PageRequest.of(0, 10);
    private String motCle = "";
    private final
    ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final Outils outils;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, RoleRepository roleRepository, Outils outils) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.outils = outils;
    }

    public Page<Client> getAll() {
        return clientRepository.findAllByNomContaining(motCle, pageable);
    }

    public Optional<Client> findById(Long code) {
        return clientRepository.findById(code);
    }

    public Client save(Client Client) {
        return clientRepository.save(Client);
    }


    @Override
    public Client save(ClientForm clientForm) {


        Role role = roleRepository.findByName("CLIENT");
        clientForm.getClient().getUser().setRole(role);
        clientForm.getClient().getUser().setPassword(outils.generatePassword(8));
        clientForm.getCompteCourant().setDateCreation(new Date());
        clientForm.getClient().setComptes(Stream.of(clientForm.getCompteCourant()).collect(Collectors.toList()));
        return this.save(clientForm.getClient());
    }

    public void delete(Long code) {
        clientRepository.deleteById(code);
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
