package com.banque.services.client;

import com.banque.models.Client;
import com.banque.models.forms.ClientForm;
import com.banque.services.Service;
import org.springframework.data.domain.Page;

public interface ClientService extends Service<Client, Long> {

    public void setMotCle(String motCle);

    public String getMotCle();

    Client save(ClientForm data);
}
