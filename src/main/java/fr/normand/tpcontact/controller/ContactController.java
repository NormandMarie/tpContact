package fr.normand.tpcontact.controller;

import fr.normand.tpcontact.service.ContactService;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/home")
public class ContactController {
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
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
                model.addAttribute("user", user);
//                System.out.println(user.getEmail() + " patarte ");
                model.addAttribute("contacts", contacts);
                return "home";
            }
        }
        return "login";
    }

    @PostMapping("/save-contacts")
    public String createContact(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
                                @RequestParam String phoneNumber, @RequestParam Long userId, Model model) {
        User user = userService.findById(userId);
        Contact contact = new Contact(firstName, lastName, email, phoneNumber, user);
        contactService.save(contact);
        List<Contact> contacts = user.getContacts();
        contacts.add(contact);
        userService.save(user);
        model.addAttribute("user", user);
        model.addAttribute("contacts", contacts);
        return "home";
    }
}

//    @PostMapping("/save-contacts")
//    public String createContact(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
//                                @RequestParam String phoneNumber, @ModelAttribute User user, Model model) {
//        System.out.println(user.getEmail());
//        userService.save(user);
//        Contact contact = new Contact(firstName, lastName, email, phoneNumber, user);
//        contactService.save(contact);
//        List<Contact> contacts = user.getContacts();
//        contacts.add(contact);
//        model.addAttribute("user", user);
//        model.addAttribute("contacts", contacts);
//        return "home";
//    }
//}


//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/save-contacts")
//    public String createContact(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String phoneNumber, HttpServletRequest request) {
//        Principal principal = request.getUserPrincipal();
//        if (principal != null) {
//            String email = principal.getName();
//            User user = userService.findByEmail(email);
//            if (user != null) {
//                Contact contact = new Contact(firstName, lastName, email, phoneNumber, user);
//                contactService.save(contact);
//                return "home";
//            }
//            return "erreur " + principal;
//        }
//        return firstName;
//    }

