package com.banque.models.compte;

import com.banque.models.Client;
import com.banque.models.operation.Operation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_CPTE", discriminatorType = DiscriminatorType.STRING, length = 2)
public class Compte {
    @Id
    protected String code;
    protected double solde;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date dateCreation;
    @ManyToOne(cascade = CascadeType.ALL)
    protected Client client;
    @OneToMany(mappedBy = "compte")
    protected Collection<Operation> operations;


    public Compte(String code) {
        this.code = code;
    }
}
