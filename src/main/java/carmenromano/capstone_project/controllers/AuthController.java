package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.exceptions.BadRequestException;
import carmenromano.capstone_project.payload.CustomerLoginPayload;
import carmenromano.capstone_project.payload.CustomerResponseAuthPayload;
import carmenromano.capstone_project.payload.CustomerResponsePayload;
import carmenromano.capstone_project.payload.NewCustomerPayload;
import carmenromano.capstone_project.services.AuthService;
import carmenromano.capstone_project.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public CustomerResponseAuthPayload login(@RequestBody CustomerLoginPayload payload) {
        return new CustomerResponseAuthPayload(authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponsePayload saveUser(@Valid @RequestBody NewCustomerPayload body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new CustomerResponsePayload(this.customerService.save(body).getId());
    }
}