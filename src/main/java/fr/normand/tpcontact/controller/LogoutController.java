package fr.normand.tpcontact.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController {
    @GetMapping
    public String logout(HttpSession session) {
        // Supprimer la session de l'utilisateur connecté
        session.invalidate();

        // Rediriger vers la page de connexion
        return "login";
    }
}
