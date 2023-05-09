package fr.normand.tpcontact.controller;

import org.springframework.ui.Model;
import fr.normand.tpcontact.entity.User;
import fr.normand.tpcontact.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {
    @Autowired
    private UserService userService;
@GetMapping
    public String registerUser(Model model) {
        // Créer un nouvel objet User pour stocker les données du formulaire
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        userService.save(user);
        return "redirect:/home";
    }
}
