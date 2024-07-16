package carmenromano.capstone_project.services;

import carmenromano.capstone_project.entities.Comune;
import carmenromano.capstone_project.entities.Provincia;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.repositories.ComuneRepository;
import carmenromano.capstone_project.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class ComuneService {
    @Autowired
    ComuneRepository comuneRepository;
    @Autowired
    ProvinciaService provinciaService;
    @Autowired
    ProvinciaRepository provinciaRepository;



    public List<Comune> findByNameAndProvincia(String name, Provincia provincia) {
        List<Comune> comuni = comuneRepository.findByNameAndProvincia(name, provincia.getName());

        if (comuni.isEmpty()) {
            throw new NotFoundException("Nessun comune trovato con il nome '" + name + "' nella provincia '" + provincia.getName() + "'");
        }

        return comuni;
    }


    public List<Comune> findByProvinciaId(UUID provinciaId){
        Optional<Provincia> provincia = provinciaRepository.findById(provinciaId);
        if (provincia.isPresent()) {
            return comuneRepository.findByProvincia(provincia.get().getName());
        } else {
            throw new NotFoundException("Provincia non trovata");
        }
    }
}
