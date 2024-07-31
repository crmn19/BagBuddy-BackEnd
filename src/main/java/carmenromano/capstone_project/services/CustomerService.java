package carmenromano.capstone_project.services;


import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.Indirizzo;
import carmenromano.capstone_project.exceptions.BadRequestException;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.payload.NewCustomerPayload;
import carmenromano.capstone_project.payload.PasswordResetPayload;
import carmenromano.capstone_project.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder bcrypt;


    public Page<Customer> getCustomers(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return customerRepository.findAll(pageable);
    }

    public Customer save(NewCustomerPayload body) {
        this.customerRepository.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );

        Customer customer = new Customer();
        customer.setEmail(body.email());
        customer.setPassword(bcrypt.encode(body.password()));
        customer.setNome(body.nome());
        customer.setCognome(body.cognome());
        customer.setDataDiNascita(body.dataDiNascita());
        customer.setSesso(body.sesso());
        customer.setCreatedAt(LocalDate.now());
        Customer saved = customerRepository.save(customer);


        return saved;
    }

    public Customer findById(UUID customerId) {
        return this.customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException(customerId));
    }

    public Customer findByIdAndUpdate(UUID userId, Customer modifiedCustomer) {
        Customer found = this.findById(userId);
        found.setEmail(modifiedCustomer.getEmail());
        found.setNome(modifiedCustomer.getNome());
        found.setCognome(modifiedCustomer.getCognome());
        found.setDataDiNascita(modifiedCustomer.getDataDiNascita());
        found.setSesso(modifiedCustomer.getSesso());
        return this.customerRepository.save(found);
    }
    public Customer findByIdAndUpdatePassword(UUID userId, PasswordResetPayload passwordResetPayload) {
        Customer found = this.findById(userId);
        if (found == null) {
            throw new NotFoundException("Customer not found");
        }
        if (!bcrypt.matches(passwordResetPayload.oldPassword(), found.getPassword())) {
            throw new IllegalArgumentException("The old password is incorrect.");
        }
        if (passwordResetPayload.newPassword() == null || passwordResetPayload.newPassword().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be null or empty.");
        }
        found.setPassword(bcrypt.encode(passwordResetPayload.newPassword()));
        return this.customerRepository.save(found);
    }



    public void findByIdAndDelete(UUID userId) {
        Customer found = this.findById(userId);
        this.customerRepository.delete(found);
    }

    public Customer findByEmail(String email){
        return customerRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }
    public Customer uploadIndirizzo(Indirizzo indirizzo, Customer customer) {
        Customer found = this.findById(customer.getId());
        indirizzo.setCustomer(found);
        found.setIndirizzo(indirizzo);
        return customerRepository.save(found);
    }

}