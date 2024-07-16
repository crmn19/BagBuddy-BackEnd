package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Cart;
import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.Product;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.payload.CartResponsePayload;
import carmenromano.capstone_project.services.CartService;
import carmenromano.capstone_project.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/me")
    public Customer getProfile(@AuthenticationPrincipal Customer currentAuthenticatedUser) {
        if (currentAuthenticatedUser == null) {
            logger.error("No authenticated customer found");
            throw new RuntimeException("Authenticated customer not found");
        }
        logger.info("Authenticated customer: " + currentAuthenticatedUser.getEmail());

        return currentAuthenticatedUser;
    }
    @GetMapping("/{customerId}")
    public Customer findById(@PathVariable UUID customerId) {
        return this.customerService.findById(customerId);
    }

    @PutMapping("/{customerId}")
    public Customer findByIdAndUpdate(@PathVariable UUID customerId, @RequestBody Customer body) {
        return customerService.findByIdAndUpdate(customerId, body);
    }


    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID customerId) {
        customerService.findByIdAndDelete(customerId);
    }


    @PostMapping("/add/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponsePayload addToCart(@AuthenticationPrincipal Customer currentAuthenticatedUser,
                                         @PathVariable UUID productId) {
        return cartService.aggiungiAlCarrello(currentAuthenticatedUser.getId(), productId);
    }
    }



