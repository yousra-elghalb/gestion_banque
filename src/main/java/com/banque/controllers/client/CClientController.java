package com.banque.controllers.client;

import com.banque.models.Client;
import com.banque.services.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller()
@RequestMapping("c/client")
public class CClientController {

    private String prefixClient = "client/";
    private final ClientService clientService;

    @Autowired
    public CClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @RequestMapping(value = "/updatePageable", method = RequestMethod.POST)
    public String updatePageable(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        clientService.setPageable(PageRequest.of(page, size));

        return "redirect:/c/client/";
    }

    @RequestMapping(value = "/updateMotCle", method = RequestMethod.POST)
    public String updateMotCle(@RequestParam(value = "motCle", defaultValue = "") String motCle) {
        clientService.setMotCle(motCle);
        return "redirect:/c/client/";
    }

    @RequestMapping("/")
    public String index(Model model) {
        return "client/list";
    }


    @ModelAttribute
    public void getClients(Model model) {
        Page<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients.getContent());
        model.addAttribute("page", clients);
        model.addAttribute("motCle", clientService.getMotCle());


        int totalPages = clients.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
