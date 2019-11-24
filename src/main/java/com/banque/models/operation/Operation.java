package com.banque.models.operation;

import com.banque.models.compte.Compte;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TypeOperation", length = 1)
public class Operation {
    @Id
    @GeneratedValue
    private long id;
    private String numOperation;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOperation;
    @Min(0)
    private double montant;


    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;

    public Operation() {
    }

    public Operation(Compte compte) {
        this.compte = compte;
    }

    public Operation(String numOperation, Date dateOperation, double montant) {
        this.numOperation = numOperation;
        this.dateOperation = dateOperation;
        this.montant = montant;
    }

    public Operation(@Min(0) double montant, Compte compte) {
        this.montant = montant;
        this.compte = compte;
    }

    public Operation(String numOperation, Date dateOperation, double montant, Compte compte) {
        this.numOperation = numOperation;
        this.dateOperation = dateOperation;
        this.montant = montant;
        this.compte = compte;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumOperation() {
        return numOperation;
    }

    public void setNumOperation(String numOperation) {
        this.numOperation = numOperation;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
