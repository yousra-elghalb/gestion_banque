package com.banque.models.operation;

import com.banque.models.compte.Compte;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@DiscriminatorValue("v")
public class Versement extends Operation {
    public Versement(String numOperation, Date dateOperation, double montant) {
        super(numOperation, dateOperation, montant);
    }

    public Versement(@Min(0) double montant, Compte compte) {
        super(montant, compte);
    }

    public Versement(Compte compte) {
        super(compte);
    }

    public Versement() {
    }
}
