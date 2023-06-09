package fr.normand.tpcontact.service;

import fr.normand.tpcontact.entity.Contact;
import fr.normand.tpcontact.entity.User;
import fr.normand.tpcontact.repository.ContactRepository;
import fr.normand.tpcontact.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public void save(Contact contact) {
        contactRepository.save(contact);
    }

    public List<Contact> findAllByUser(User user) {
        return contactRepository.findAllByUser(user);
    }
    public void deleteById(Long id) {
        contactRepository.deleteById(id);
    }

    public Contact findById(Long id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        return  optionalContact.orElse(null);
    }

    public List<Contact> searchContactsByName(String searchText, User user) {
        List<Contact> contacts = contactRepository.findAllByUser(user);
        List<Contact> matchingContacts = new ArrayList<>();

        for (Contact contact : contacts) {
            if (contact.getFirstName().toLowerCase().contains(searchText.toLowerCase())
                    || contact.getLastName().toLowerCase().contains(searchText.toLowerCase())) {
                matchingContacts.add(contact);
            }
        }

        return matchingContacts;
    }

}
