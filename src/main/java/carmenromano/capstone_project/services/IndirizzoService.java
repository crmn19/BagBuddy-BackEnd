package carmenromano.capstone_project.services;

import carmenromano.capstone_project.entities.Comune;
import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.Indirizzo;
import carmenromano.capstone_project.entities.Provincia;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.payload.IndirizzoPayload;
import carmenromano.capstone_project.repositories.ComuneRepository;
import carmenromano.capstone_project.repositories.CustomerRepository;
import carmenromano.capstone_project.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ComuneService comuneService;

    public Indirizzo save(IndirizzoPayload payload, UUID customerId) throws IOException {
        Provincia provincia = provinciaService.findByName(payload.provincia());
        if (provincia == null) {
            throw new IOException("Provincia non trovata con il nome: " + payload.provincia());
        }

        Customer customer = customerService.findById(customerId);
        if (customer == null) {
            throw new IOException("Cliente non trovato con ID: " + customerId);
        }

        Comune comune = comuneService.findByName(payload.comune());
        if (comune == null) {
            throw new IOException("Comune non trovato con il nome: " + payload.comune());
        }
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(payload.via());
        indirizzo.setCivico(payload.civico());
        indirizzo.setCap(payload.cap());
        indirizzo.setProvincia(provincia);
        indirizzo.setComune(comune);
        indirizzo.setCustomer(customer);

        return indirizzoRepository.save(indirizzo);
    }


    public Indirizzo findById(Long id) {
        return indirizzoRepository.findById(id).orElseThrow(() -> new NotFoundException("Nessun indirizzo è stato trovato con l'id: " + id));
    }

    public Indirizzo findByCustomer(Customer customer) {
        return indirizzoRepository.findByCustomer(customer);
    }

    public void findByIdAndDelete(Long id) {
        Indirizzo found = this.findById(id);
        indirizzoRepository.delete(found);
    }
}
