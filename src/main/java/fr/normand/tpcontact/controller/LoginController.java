package fr.normand.tpcontact.controller;

import fr.normand.tpcontact.entity.Contact;
import fr.normand.tpcontact.entity.User;
import fr.normand.tpcontact.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String loginPage() {
        return "login";
    }
@PostMapping(value = "saveUser")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
    User user = userService.findByEmailAndPassword(email, password);
    System.out.println(user.getLastName());
    System.out.println(user.getContacts());
    if (user != null) {
          List<Contact> contacts = user.getContacts();

          model.addAttribute("contacts", contacts);
        return "home";
    } else {
        // Erreur de connexion
        return "login";
    }
    }
}

