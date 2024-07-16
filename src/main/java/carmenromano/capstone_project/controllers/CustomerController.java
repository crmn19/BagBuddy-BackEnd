package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/me")
    public Customer getProfile(@AuthenticationPrincipal Customer currentAuthenticatedUser) {
        if (currentAuthenticatedUser == null) {
            logger.error("No authenticated customer found");
            throw new RuntimeException("Authenticated customer not found");
        }

        // Log per verificare l'oggetto currentAuthenticatedUser
        logger.info("Authenticated customer: " + currentAuthenticatedUser.getEmail());

        return currentAuthenticatedUser;
    }
}
