package com.banque.services.operation;

import com.banque.models.Client;
import com.banque.models.compte.Compte;
import com.banque.models.operation.Operation;
import com.banque.models.operation.Retrait;
import com.banque.models.operation.Versement;
import com.banque.models.operation.Virement;
import com.banque.services.Service;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface OperationService extends Service<Operation, Long> {


    Page<Operation> findAllByCompte(Compte compte);

    Page<Operation> findAllByCompteClientUserId(long id);

    //    Page<Operation> findAllByNumOperationContaining(Compte compte);
    void verser(Versement versement) throws Exception;

    void retirer(Retrait retrait) throws Exception;

    void virement(Virement virement) throws Exception;
}
