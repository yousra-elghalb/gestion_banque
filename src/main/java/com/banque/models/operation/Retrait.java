package com.banque.models.operation;

import com.banque.models.compte.Compte;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@DiscriminatorValue("r")
public class Retrait extends Operation {
    public Retrait(String numOperation, Date dateOperation, double montant) {
        super(numOperation, dateOperation, montant);
    }

    public Retrait(String numOperation, Date dateOperation, double montant, Compte compte) {
        super(numOperation, dateOperation, montant, compte);
    }

    public Retrait(@Min(0) double montant, Compte compte) {
        super(montant, compte);
    }

    public Retrait(Compte compte) {
        super(compte);
    }

    public Retrait() {
    }
}
