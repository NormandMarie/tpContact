package fr.normand.tpcontact.controller;

import fr.normand.tpcontact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/delete-contact")
public class DeleteController {
    @Autowired
    private ContactService contactService;
    @GetMapping
    public String deleteContact(@RequestParam Long id) {
        contactService.deleteById(id);
        return "redirect:/home";
    }
}
