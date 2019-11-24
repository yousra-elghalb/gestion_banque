package com.banque.models.compte;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CC")
@Data
public class CompteCourant extends Compte {
    private double decouvet;


    public CompteCourant() {
    }

}
