package com.banque.services.operation;

import com.banque.models.compte.Compte;
import com.banque.models.compte.CompteCourant;
import com.banque.models.operation.Operation;
import com.banque.models.operation.Retrait;
import com.banque.models.operation.Versement;
import com.banque.models.operation.Virement;
import com.banque.repositories.CompteRepository;
import com.banque.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {
    private Pageable pageable = PageRequest.of(0, 10);
    private String motCle = "";
    private String dateCle = "";
    private final
    OperationRepository operationRepository;

    private final CompteRepository compteRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository, CompteRepository compteRepository) {
        this.operationRepository = operationRepository;
        this.compteRepository = compteRepository;
    }

    @Override
    public Page<Operation> findAllByCompte(Compte compte) {
        return operationRepository.findAllByCompte(compte, pageable);
    }

    @Override
    public Page<Operation> findAllByCompteClientUserId(long id) {
        return operationRepository.findAllByCompteClientUserId(id, pageable);
    }

    public Page<Operation> getAll() {
        return operationRepository.findAll(pageable);
    }


    @Override
    public void verser(Versement versement) throws Exception {

        Compte cp = compteRepository.findByCode(versement.getCompte().getCode());
        if (cp == null)
            throw new Exception("compte not found");
        versement.setDateOperation(new Date());
        versement.setCompte(cp);
        operationRepository.save(versement);
        cp.setSolde(cp.getSolde() + versement.getMontant());
        compteRepository.save(cp);
    }

    @Override
    public void retirer(Retrait retrait) throws Exception {
        System.out.println("retrait = [" + retrait.getCompte() + "]");
        Compte cp = compteRepository.findByCode(retrait.getCompte().getCode());
        if (cp == null)
            throw new Exception("compte not found");
        double facilitesCaisse = 0;
        if (cp instanceof CompteCourant)
            facilitesCaisse = ((CompteCourant) cp).getDecouvet();
        if (cp.getSolde() + facilitesCaisse <
                retrait.getMontant())
            throw new RuntimeException("Solde Insufisant");
        retrait.setDateOperation(new Date());
        retrait.setCompte(cp);
        operationRepository.save(retrait);
        cp.setSolde(cp.getSolde() - retrait.getMontant());
        compteRepository.save(cp);
    }

    @Override
    public void virement(Virement virement) throws Exception {
        if (virement.getCode_des().equals(virement.getCode_src()))
            throw new RuntimeException("Impossible d'effectuer un verment sur le meme compte");

        Versement versement = new Versement(virement.getMontant(), new Compte(virement.getCode_des()));
        Retrait retrait = new Retrait(virement.getMontant(), new Compte(virement.getCode_src()));

        retirer(retrait);
        verser(versement);
    }


    public Optional<Operation> findById(Long code) {
        return operationRepository.findById(code);
    }

    public Operation save(Operation Operation) {
        return operationRepository.save(Operation);
    }


    public void delete(Long code) {
        operationRepository.deleteById(code);
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

    public String getDateCle() {
        return dateCle;
    }

    public void setDateCle(String dateCle) {
        this.dateCle = dateCle;
    }
}
