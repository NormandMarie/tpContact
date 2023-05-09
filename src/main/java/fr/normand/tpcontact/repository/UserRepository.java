package fr.normand.tpcontact.repository;

import fr.normand.tpcontact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
