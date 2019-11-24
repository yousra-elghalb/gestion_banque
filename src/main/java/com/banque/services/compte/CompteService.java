package com.banque.services.compte;

import com.banque.models.Client;
import com.banque.models.compte.Compte;
import com.banque.models.compte.CompteCourant;
import com.banque.services.Service;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface CompteService extends Service<Compte, String> {

    public void setMotCle(String motCle);

    public String getMotCle();

    Collection<Compte> getByClientCode(long code);

    Page<Compte> getByUserId(long id);

//    Compte saveClient(CompteCourant data);
}
