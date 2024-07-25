package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Comune;
import carmenromano.capstone_project.entities.Provincia;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.repositories.ProvinciaRepository;
import carmenromano.capstone_project.services.ComuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/provincia")
public class ProvinceController {
    @Autowired
    private ComuneService comuneService;
    @Autowired
    private ProvinciaRepository provinciaRepository;


    @GetMapping("/{provinciaId}")
    public List<Comune> getComuniByProvincia(@PathVariable UUID provinciaId) {
        List<Comune> comuni = comuneService.findByProvinciaId(provinciaId);
        if (comuni.isEmpty()) {
            throw new NotFoundException("Nessun comune trovato per la provincia con ID: " + provinciaId);
        }
        return comuni;
    }

    @GetMapping
    public List<Provincia> getAllProvince() {
        return provinciaRepository.findAll();
    }

}

