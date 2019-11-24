package com.banque.models.operation;

import com.banque.models.compte.Compte;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;


public class Virement {

    private String code_src;
    private String code_des;
    private double montant;

    public Virement(String code_src) {
        this.code_src = code_src;
    }

    public String getCode_src() {
        return code_src;
    }

    public void setCode_src(String code_src) {
        this.code_src = code_src;
    }

    public String getCode_des() {
        return code_des;
    }

    public void setCode_des(String code_des) {
        this.code_des = code_des;
    }


    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
