package fr.normand.tpcontact.controller;

import fr.normand.tpcontact.entity.User;
import fr.normand.tpcontact.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(value = "/profil")
public class ProfilController {
    @Autowired
    private UserService userService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "profil";
        } else {
            return "redirect:/home";
        }
    }
    @PostMapping("/{id}")
    public String updateProfile(@PathVariable Long id, @ModelAttribute User user, Model model) {
        User existingUser = userService.findById(id);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhotoUrl(user.getPhotoUrl());
            existingUser.setPassword(user.getPassword());

            userService.save(existingUser);
            model.addAttribute("user", existingUser);
            return "profil";
        } else {
            return "redirect:/home";
        }
    }



}
