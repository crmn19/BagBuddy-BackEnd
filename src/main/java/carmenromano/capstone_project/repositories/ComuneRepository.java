package carmenromano.capstone_project.repositories;


import carmenromano.capstone_project.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, UUID> {
    List<Comune> findByProvincia(String provincia);
    List<Comune> findByNameAndProvincia(String name, String provincia);
}
