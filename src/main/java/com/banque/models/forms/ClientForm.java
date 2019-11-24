package com.banque.models.forms;

import com.banque.models.Client;
import com.banque.models.compte.CompteCourant;
import com.banque.models.compte.CompteEpargne;

public class ClientForm {
    Client client;
    CompteCourant compteCourant;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CompteCourant getCompteCourant() {
        return compteCourant;
    }

    public void setCompteCourant(CompteCourant compteCourant) {
        this.compteCourant = compteCourant;
    }


}
