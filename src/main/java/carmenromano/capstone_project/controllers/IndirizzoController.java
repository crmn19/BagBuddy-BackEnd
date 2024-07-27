package carmenromano.capstone_project.controllers;


import carmenromano.capstone_project.entities.Comune;
import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.Indirizzo;
import carmenromano.capstone_project.entities.Provincia;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.payload.IndirizzoPayload;
import carmenromano.capstone_project.repositories.IndirizzoRepository;
import carmenromano.capstone_project.services.ComuneService;
import carmenromano.capstone_project.services.CustomerService;
import carmenromano.capstone_project.services.IndirizzoService;
import carmenromano.capstone_project.services.ProvinciaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {
    @Autowired
    CustomerService customerService;
    @Autowired
    IndirizzoService indirizzoService;
    @PostMapping("/create")
    public Customer createIndirizzo(@RequestBody IndirizzoPayload body,
                                    @AuthenticationPrincipal Customer cliente) throws IOException {
        Indirizzo indirizzo;
        try {
            indirizzo = indirizzoService.save(body, cliente.getId());
        } catch (IOException e) {
            throw new BadRequestException("Errore durante il salvataggio dell'indirizzo: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
        return customerService.uploadIndirizzo(indirizzo, cliente);
    }

    @GetMapping("/find")
    public Indirizzo findByCustomer(@AuthenticationPrincipal Customer cliente){
        return indirizzoService.findByCustomer( cliente);
    }
}