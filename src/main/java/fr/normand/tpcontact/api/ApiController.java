package fr.normand.tpcontact.api;

import fr.normand.tpcontact.entity.Contact;
import fr.normand.tpcontact.entity.ContactDTO;
import fr.normand.tpcontact.entity.User;
import fr.normand.tpcontact.service.ContactService;
import fr.normand.tpcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/{userId}/contacts")
public class ApiController {


        @Autowired
        private ContactService contactService;
    @Autowired
    private UserService userService;

        // Get all contacts
//        @GetMapping
//        public ResponseEntity<List<Contact>> getAllContacts(@PathVariable Long userId) {
//            User user = userService.findById(userId);
//            if (user == null) {
//                return ResponseEntity.notFound().build();
//            }
//            List<Contact> contacts = user.getContacts();
//            return ResponseEntity.ok().body(contacts);
//        }
        @GetMapping
        public ResponseEntity<List<ContactDTO>> getAllContacts(@PathVariable Long userId) {
            User user = userService.findById(userId);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            List<Contact> contacts = user.getContacts();
            List<ContactDTO> contactDTOs = contacts.stream()
                    .map(contact -> new ContactDTO(
                            contact.getId(),
                            contact.getFirstName(),
                            contact.getLastName(),
                            contact.getEmail(),
                            contact.getPhoneNumber()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(contactDTOs);
        }
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long userId, @PathVariable Long id) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Contact contact = contactService.findById(id);
        if (contact == null || !user.getContacts().contains(contact)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(contact);
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@PathVariable Long userId, @RequestBody Contact contact) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        contact.setUser(user);
         contactService.save(contact);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contact.getId())
                .toUri();
        return ResponseEntity.created(location).body(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long userId, @PathVariable Long id,
                                                 @RequestBody Contact contact) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Contact existingContact = contactService.findById(id);
        if (existingContact == null || !user.getContacts().contains(existingContact)) {
            return ResponseEntity.notFound().build();
        }
        existingContact.setFirstName(contact.getFirstName());
        existingContact.setLastName(contact.getLastName());
        existingContact.setEmail(contact.getEmail());
        existingContact.setPhoneNumber(contact.getPhoneNumber());
         contactService.save(existingContact);
        return ResponseEntity.ok().build();
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Contact> deleteContact(@PathVariable Long userId, @PathVariable Long id) {
//        User user = userService.findById(userId);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//        Contact contact = contactService.findById(id);
//        if (contact == null || !user.getContacts().contains(contact)) {
//            return ResponseEntity.notFound().build();
//        }
//        contactService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteContact(@PathVariable Long userId, @PathVariable Long id) {
    User user = userService.findById(userId);
    if (user == null) {
        return ResponseEntity.notFound().build();
    }
    Contact contact = contactService.findById(id);
    if (contact == null || !user.getContacts().contains(contact)) {
        return ResponseEntity.notFound().build();
    }
    List<Contact> contacts = user.getContacts();
    contacts.remove(contact);
    contactService.deleteById(id);
    return ResponseEntity.noContent().build();
}

}

