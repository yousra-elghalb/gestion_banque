package com.banque.controllers.user;

import com.banque.models.compte.Compte;
import com.banque.models.compte.CompteCourant;
import com.banque.models.operation.Operation;
import com.banque.models.operation.Retrait;
import com.banque.models.operation.Versement;
import com.banque.models.operation.Virement;
import com.banque.services.compte.CompteService;
import com.banque.services.operation.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("u/operation")
public class UOperationController {


    @Autowired
    private CompteService compteService;
    @Autowired
    private OperationService operationService;

    @RequestMapping("/form")
    public String operation(@RequestParam("code") String code, Model model) {
        Compte compte = compteService.findById(code).orElse(null);
        if (compte == null)
            return "redirect:/u/compte/list";
        model.addAttribute("compte", compte);
        model.addAttribute("versement", new Versement(new Compte(code)));
        model.addAttribute("retrait", new Retrait(new Compte(code)));
        model.addAttribute("virement", new Virement(code));
        return "operation/form";
    }

    @PostMapping("/retrait")
    public String retrait(@Valid Retrait retrait, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "operation/form";
        }
        try {
            operationService.retirer(retrait);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/u/operation/form?code=" + retrait.getCompte().getCode();
    }

    @PostMapping("/virement")
    public String virement(@Valid Virement virement, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "operation/form";
        }
        try {
            operationService.virement(virement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/u/operation/form?code=" + virement.getCode_src();
    }

    @PostMapping("/versement")
    public String versement(@Valid Versement versement, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "operation/form";
        }
        System.out.println("versement = [" + versement + "], bindingResult = []");
        try {
            operationService.verser(versement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/u/operation/form?code=" + versement.getCompte().getCode();
    }

    @RequestMapping("/list")
    public String list() {
        return "operation/list";
    }

    @RequestMapping("/")
    public String index(int size, int page) {
        operationService.setPageable(PageRequest.of(page, size));
        return "redirect:/u/operation/list";
    }

    @ModelAttribute
    public void getComptes(Model model) {
        Page<Operation> operation = operationService.getAll();
        model.addAttribute("operations", operation.getContent());
        model.addAttribute("page", operation);


        int totalPages = operation.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
