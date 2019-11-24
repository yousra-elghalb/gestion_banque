package com.banque.controllers.user;

import com.banque.models.Client;
import com.banque.models.compte.Compte;
import com.banque.models.compte.CompteCourant;
import com.banque.services.client.ClientService;
import com.banque.services.compte.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller()
@RequestMapping("u/client")
public class UClientController {

    private final ClientService clientService;
    private final CompteService compteService;


    @Autowired
    public UClientController(ClientService clientService, CompteService compteService) {
        this.clientService = clientService;
        this.compteService = compteService;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("compteCourant", new CompteCourant());
        return "client/form";
    }

    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam("code") long code) {
        Client client = clientService.findById(code).orElse(null);
        if (client == null)
            return "client/list";
        System.out.println("model = [" + client + "]");
        model.addAttribute("client", client);
        return "client/form-edit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("code") long code) {
        clientService.delete(code);
        return "redirect:/c/client/";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid CompteCourant compteCourant, BindingResult bindingResult) {
        System.out.println("compte = [" + compteCourant + "]");
        System.out.println("compte = [" + compteCourant.getClient() + "]");

        if (bindingResult.hasErrors()) {
            return "client/form";
        }

        compteService.save(compteCourant);
        return "redirect:/c/client/";
    }

    @RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
    public String saveEdit(@Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/form-edit";
        }

        clientService.save(client);
        return "redirect:/c/client/";
    }
}
