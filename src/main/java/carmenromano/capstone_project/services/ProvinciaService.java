package carmenromano.capstone_project.services;

import carmenromano.capstone_project.entities.Provincia;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinciaService {
    @Autowired
    ProvinciaRepository provinciaRepository;

    public Provincia findBySigla(String sigla){
        return provinciaRepository.findBySigla(sigla).orElseThrow(()-> new NotFoundException("Provincia non trovata"));
    }
    public Provincia findByName(String name){
        return provinciaRepository.findByName(name).get(0);
    }
}
