package fr.normand.tpcontact.controller;

import fr.normand.tpcontact.entity.Contact;
import fr.normand.tpcontact.entity.User;
import fr.normand.tpcontact.service.ContactService;
import fr.normand.tpcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/edit")
public class editContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/{userId}/{contactId}", method = RequestMethod.GET)
    public String editContact(@PathVariable("userId") Long userId, @PathVariable("contactId") Long contactId, Model model) {
        User user = userService.findById(userId);
        Contact contact = contactService.findById(contactId);
        model.addAttribute("user", user);
        model.addAttribute("contact", contact);
        return "edit-contact";
    }
    @RequestMapping(value = "/{userId}/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("userId") Long userId, @PathVariable("contactId") Long contactId,
                                @ModelAttribute Contact contact, Model model) {
        // récupérez le contact à mettre à jour en utilisant l'id du contact
        Contact contactup = contactService.findById(contactId);

        // effectuez les mises à jour nécessaires
        contactup.setFirstName(contact.getFirstName());
        contactup.setLastName(contact.getLastName());
        contactup.setEmail(contact.getEmail());
        contactup.setPhoneNumber(contact.getPhoneNumber());
        contactService.save(contactup);

        // ajoutez les attributs au modèle
        User user = userService.findById(userId);
        List<Contact> contacts = contactService.findAllByUser(user);
        model.addAttribute("contacts", contacts);
        model.addAttribute("user", user);
        model.addAttribute("contact", contactup);

        return "home";
    }

//    @PostMapping(value = "/{userId}/{contactId}")
//    public String updateContact(@PathVariable("userId") Long userId, @PathVariable("contactId") Long contactId,
//                                @ModelAttribute Contact contact, Model model) {
//        // récupérez l'utilisateur connecté en utilisant l'id de l'utilisateur
//        User user = userService.findById(userId);
//
//        // récupérez le contact à mettre à jour en utilisant l'id du contact
//        Contact contactup = contactService.findById(contactId);
//
//        // effectuez les mises à jour nécessaires
//        contactup.setFirstName(contact.getFirstName());
//        contactup.setLastName(contact.getLastName());
//        contactup.setEmail(contact.getEmail());
//        contactup.setPhoneNumber(contact.getPhoneNumber());
//        contactService.save(contactup);
//
//        // ajoutez les attributs au modèle
//        model.addAttribute("user", user);
//        model.addAttribute("contact", contactup);
//
//        return "home";
//    }


//    @PostMapping("{id}")
//    public String updateContact(@PathVariable("id") Long id,@ModelAttribute Contact contact,@ModelAttribute User user,Model model) {
//        Contact contactup = contactService.findById(id);
//        if (contactup != null) {
//            contactup.setFirstName(contact.getFirstName());
//            contactup.setLastName(contact.getLastName());
//            contactup.setEmail(contact.getEmail());
//            contactup.setPhoneNumber(contact.getPhoneNumber());
//
//
//            contactService.save(contactup);
//            model.addAttribute("contact", contactup);
//            model.addAttribute("user", user);
//            return "home";
//        } else {
//            return "erreur";
//        }
//    }
}
