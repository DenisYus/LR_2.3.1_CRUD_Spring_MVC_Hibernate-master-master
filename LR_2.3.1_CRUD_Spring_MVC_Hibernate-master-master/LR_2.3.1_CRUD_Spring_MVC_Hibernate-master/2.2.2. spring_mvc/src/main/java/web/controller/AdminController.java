package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDAO;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private UserService dao;


    public AdminController(UserService dao) {
        this.dao = dao;
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    
    @GetMapping("/people")
    public String index(Model model) {
        model.addAttribute("people", dao.getAllUsers());
        return "view/index";
    }

    @PostMapping("/people")
    public String creat(@ModelAttribute("newUser") @Valid User user,
                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("people", dao.getAllUsers());
            return "view/index";
        }
        dao.saveUser(user);
        return "redirect:/people";
    }

    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        dao.removeUserById(id);
        return "redirect:/people";
    }

    @GetMapping("/people/{id}/edit")
    public String edit(@ModelAttribute("id") int id, Model model) {
        model.addAttribute("user", dao.getUserById(id));
        return "view/edit";
    }

    @PatchMapping("/people/{id}")
    public String updatePerson(@ModelAttribute("user") @Valid User updateuser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "view/edit";
        }
        dao.updateUser(updateuser);
        return "redirect:/people";
    }
}