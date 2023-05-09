package fr.normand.tpcontact.service;

import fr.normand.tpcontact.entity.Contact;
import fr.normand.tpcontact.entity.User;
import fr.normand.tpcontact.repository.ContactRepository;
import fr.normand.tpcontact.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
