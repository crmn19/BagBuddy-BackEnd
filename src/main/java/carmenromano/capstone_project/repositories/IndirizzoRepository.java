package carmenromano.capstone_project.repositories;

import carmenromano.capstone_project.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {

    Indirizzo findByCustomer(Customer customer);
}
