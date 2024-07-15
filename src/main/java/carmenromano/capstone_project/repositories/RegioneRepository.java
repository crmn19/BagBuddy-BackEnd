package carmenromano.capstone_project.repositories;

import carmenromano.capstone_project.entities.Regione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegioneRepository extends JpaRepository<Regione, UUID> {
}

