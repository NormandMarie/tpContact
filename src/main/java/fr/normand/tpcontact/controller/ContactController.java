package fr.normand.tpcontact.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import fr.normand.tpcontact.entity.Contact;
import fr.normand.tpcontact.entity.User;
import fr.normand.tpcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/home")
public class ContactController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String homePage(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            if (user != null) {
                List<Contact> contacts = user.getContacts();
                model.addAttribute("contacts", contacts);
                return "home";
            }
        }
        return "login";
    }
}
