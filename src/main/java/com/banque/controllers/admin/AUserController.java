package com.banque.controllers.admin;

import com.banque.models.Role;
import com.banque.models.User;
import com.banque.repositories.RoleRepository;
import com.banque.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller()
@RequestMapping("a/user")
public class AUserController {
    private final RoleRepository roleRepository;

    private final UserService userService;

    @Autowired
    public AUserController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User("", "0000"));
        return "user/form";
    }

    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam("id") long id) {
        User user = userService.findById(id).orElse(null);
        if (user == null)
            return "user/list";
        model.addAttribute("user", user);
        return "user/form";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/a/user/";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid User user, BindingResult bindingResult) {
        System.out.println("user = [" + user + "], bindingResult = [" + bindingResult + "]");
        if (bindingResult.hasErrors()) {
            return "user/form";
        }
        userService.save(user);
        return "redirect:/a/user/";
    }


    @RequestMapping(value = "/updatePageable", method = RequestMethod.POST)
    public String updatePageable(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        userService.setPageable(PageRequest.of(page, size));

        return "redirect:/a/user/";
    }

    @RequestMapping(value = "/updateMotCle", method = RequestMethod.POST)
    public String updateMotCle(@RequestParam(value = "motCle", defaultValue = "") String motCle) {
        userService.setMotCle(motCle);
        return "redirect:/a/user/";
    }

    @RequestMapping("/")
    public String index(Model model) {
        return "user/list";
    }


    @ModelAttribute("motCle")
    public String getMotCle() {
        return userService.getMotCle();
    }

    @ModelAttribute
    public void getUsers(Model model) {
        Page<User> users = userService.getAll();
        model.addAttribute("users", users.getContent());
        model.addAttribute("page", users);


        int totalPages = users.getTotalPages();
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
