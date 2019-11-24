package com.banque.repositories;

import com.banque.models.compte.Compte;
import com.banque.models.operation.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    /*  @Query("select o from  Operation o where o.compte.numCompte=:c")
      Page<Operation> getOperationsByCompte(@Param("c") String numCompte, Pageable pageable);
  */
    Page<Operation> findAllByCompte(Compte compte, Pageable pageable);

    Page<Operation> findAllByCompteClientUserId(long id, Pageable pageable);

    Page<Operation> findAll(Pageable pageable);

}
