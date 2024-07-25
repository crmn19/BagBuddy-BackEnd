package carmenromano.capstone_project.repositories;

import carmenromano.capstone_project.entities.Comune;
import carmenromano.capstone_project.entities.Indirizzo;
import carmenromano.capstone_project.entities.OrderItem;
import carmenromano.capstone_project.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {

}
