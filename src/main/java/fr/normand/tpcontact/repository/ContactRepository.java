package fr.normand.tpcontact.repository;

import fr.normand.tpcontact.entity.Contact;
import fr.normand.tpcontact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> findAllByUser(User user);
}
