package com.banque.controllers.client;

import com.banque.models.Client;
import com.banque.models.Role;
import com.banque.models.User;
import com.banque.models.compte.Compte;
import com.banque.models.compte.CompteCourant;
import com.banque.models.compte.CompteEpargne;
import com.banque.repositories.ClientRepository;
import com.banque.repositories.RoleRepository;
import com.banque.services.compte.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller()
@RequestMapping("c/compte")
public class CCompteController {
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;

    private final CompteService compteService;

    @Autowired
    public CCompteController(RoleRepository roleRepository, ClientRepository clientRepository, CompteService compteService) {
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.compteService = compteService;
    }


    @RequestMapping(value = "/updatePageable", method = RequestMethod.POST)
    public String updatePageable(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        compteService.setPageable(PageRequest.of(page, size));

        return "redirect:/c/compte/";
    }

    @RequestMapping(value = "/updateMotCle", method = RequestMethod.POST)
    public String updateMotCle(@RequestParam(value = "motCle", defaultValue = "") String motCle) {
        compteService.setMotCle(motCle);
        return "redirect:/c/compte/";
    }

    @RequestMapping("")
    public String index(Model model) {
        return "compte/client/list";
    }


    @ModelAttribute("motCle")
    public String getMotCle() {
        return compteService.getMotCle();
    }

    @ModelAttribute
    public void getComptes(Model model, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
        Page<Compte> comptes = compteService.getByUserId(user.getId());
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
