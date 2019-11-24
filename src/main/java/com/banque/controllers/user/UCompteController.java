package com.banque.controllers.user;

import com.banque.models.Client;
import com.banque.models.Role;
import com.banque.models.compte.Compte;
import com.banque.models.compte.CompteCourant;
import com.banque.models.compte.CompteEpargne;
import com.banque.repositories.ClientRepository;
import com.banque.repositories.RoleRepository;
import com.banque.services.compte.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller()
@RequestMapping("u/compte")
public class UCompteController {
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;

    private final CompteService compteService;

    @Autowired
    public UCompteController(RoleRepository roleRepository, ClientRepository clientRepository, CompteService compteService) {
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.compteService = compteService;
    }

    @RequestMapping("/addCC")
    public String addCC(Model model, @RequestParam("code") long code) {
        model.addAttribute("compte", new CompteCourant());
        Client client = clientRepository.findById(code).orElse(null);
        if (client == null)
            return "redirect:/c/client/";
        model.addAttribute("sclient", client);
        return "compte/form-courant";
    }

    @RequestMapping("/editCC")
    public String editCC(Model model, @RequestParam("code") String code) {
        Compte compte = compteService.findById(code).orElse(null);
        if (compte == null)
            return "compte/list";
        model.addAttribute("compte", (CompteCourant) compte);
        return "compte/form-courant";
    }

    @RequestMapping("/editCE")
    public String editCE(Model model, @RequestParam("code") String code) {
        Compte compte = compteService.findById(code).orElse(null);
        if (compte == null)
            return "compte/list";
        model.addAttribute("compte", (CompteCourant) compte);
        return "compte/form-epargne";
    }

    @RequestMapping("/addCE")
    public String addCE(Model model, @RequestParam("code") long code) {
        model.addAttribute("compte", new CompteEpargne());
        Client client = clientRepository.findById(code).orElse(null);
        if (client == null)
            return "redirect:/c/client/";
        model.addAttribute("sclient", client);
        return "compte/form-epargne";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("code") String code) {
        compteService.delete(code);
        return "redirect:/u/compte/";
    }

    @RequestMapping(value = "/saveCC", method = RequestMethod.POST)
    public String save(@Valid CompteCourant compte, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "compte/form-courant";
        }
        compteService.save(compte);
        return "redirect:/u/compte/";
    }

    @RequestMapping(value = "/saveCE", method = RequestMethod.POST)
    public String save(@Valid CompteEpargne compte, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "compte/form-epargne";
        }
        compteService.save(compte);
        return "redirect:/u/compte/";
    }


    @RequestMapping(value = "/updatePageable", method = RequestMethod.POST)
    public String updatePageable(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        compteService.setPageable(PageRequest.of(page, size));

        return "redirect:/u/compte/";
    }

    @RequestMapping(value = "/updateMotCle", method = RequestMethod.POST)
    public String updateMotCle(@RequestParam(value = "motCle", defaultValue = "") String motCle) {
        compteService.setMotCle(motCle);
        return "redirect:/u/compte/";
    }

    @RequestMapping("/")
    public String index(Model model) {
        return "compte/list";
    }


    @ModelAttribute("motCle")
    public String getMotCle() {
        return compteService.getMotCle();
    }

    @ModelAttribute
    public void getComptes(Model model) {
        Page<Compte> comptes = compteService.getAll();
        model.addAttribute("comptes", comptes.getContent());
        model.addAttribute("page", comptes);


        int totalPages = comptes.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
