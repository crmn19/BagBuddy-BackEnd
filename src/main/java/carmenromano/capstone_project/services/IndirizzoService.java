package carmenromano.capstone_project.services;

import carmenromano.capstone_project.entities.Comune;
import carmenromano.capstone_project.entities.Indirizzo;
import carmenromano.capstone_project.entities.Provincia;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.payload.IndirizzoPayload;
import carmenromano.capstone_project.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private ComuneService comuneService;
    @Autowired
    private ProvinciaService provinciaService;

    public Indirizzo save(IndirizzoPayload body) throws IOException {
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(body.via());
        indirizzo.setCivico(body.civico());
        indirizzo.setCap(body.cap());
        Provincia provinciafound = provinciaService.findByName(body.provincia());
        List<Comune> comunes =  comuneService.findByNameAndProvincia(body.comune(), provinciafound);
        indirizzo.setComune(comunes.getFirst());
        return indirizzoRepository.save(indirizzo);
    }
    public Indirizzo findById(Long id) {
        return indirizzoRepository.findById(id).orElseThrow(() -> new NotFoundException("Nessun indirizzo è stato trovato con l'id: " + id));
    }

    public void findByIdAndDelete(Long id) {
        Indirizzo found = this.findById(id);
        indirizzoRepository.delete(found);
    }
}
