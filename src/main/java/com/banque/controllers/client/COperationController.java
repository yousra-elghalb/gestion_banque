package com.banque.controllers.client;

import com.banque.models.User;
import com.banque.models.compte.Compte;
import com.banque.models.operation.Operation;
import com.banque.models.operation.Retrait;
import com.banque.models.operation.Versement;
import com.banque.models.operation.Virement;
import com.banque.services.compte.CompteService;
import com.banque.services.operation.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("c/operation")
public class COperationController {


    @Autowired
    private CompteService compteService;

    @Autowired
    private OperationService operationService;

    @RequestMapping("/form")
    public String operation(@RequestParam("code") String code, Model model) {
        Compte compte = compteService.findById(code).orElse(null);
        model.addAttribute("compte", compte);
        model.addAttribute("virement", new Virement(code));
        return "operation/client/form";
    }


    @PostMapping("/virement")
    public String virement(@Valid Virement virement, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "operation/client/form";
        }
        try {
            operationService.virement(virement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/c/operation/form?code=" + virement.getCode_src();
    }


    @RequestMapping("/list")
    public String list() {
        return "operation/client/list";
    }

    @RequestMapping("/")
    public String index(int size, int page) {
        operationService.setPageable(PageRequest.of(page, size));
        return "redirect:/c/operation/list";
    }


    @ModelAttribute
    public void getComptes(Model model, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
        Page<Operation> operation = operationService.findAllByCompteClientUserId(user.getId());

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
