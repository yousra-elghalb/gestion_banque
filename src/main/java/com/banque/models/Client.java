package com.banque.models;

import com.banque.models.compte.Compte;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;
    @NotNull
    @Length(min = 3, max = 30)
    private String nom;
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private Collection<Compte> comptes;
    @OneToOne(cascade = CascadeType.ALL)
    private User user = new User();

}
