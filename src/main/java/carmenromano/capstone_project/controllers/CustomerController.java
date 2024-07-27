package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.Product;
import carmenromano.capstone_project.payload.CartResponsePayload;
import carmenromano.capstone_project.payload.OrderCustomerPayload;
import carmenromano.capstone_project.repositories.CartRepository;
import carmenromano.capstone_project.services.CartService;
import carmenromano.capstone_project.services.CustomerService;
import carmenromano.capstone_project.services.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/me")
    public Customer getProfile(@AuthenticationPrincipal Customer currentAuthenticatedUser) {
        if (currentAuthenticatedUser == null) {
            throw new RuntimeException("Authenticated customer not found");
        }
        return currentAuthenticatedUser;
    }
    @GetMapping
    public Page<Customer> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.customerService.getCustomers(page, size, sortBy);
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
    public List<CartResponsePayload> addToCart(@AuthenticationPrincipal Customer currentAuthenticatedUser,
                                               @PathVariable UUID productId) {
        return cartService.aggiungiAlCarrello(currentAuthenticatedUser.getId(), productId);
    }

    @PostMapping("/remove/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CartResponsePayload> removeToCart(@AuthenticationPrincipal Customer currentAuthenticatedUser,
                                               @PathVariable UUID productId) {
        return cartService.rimuoviDalCarrello(currentAuthenticatedUser.getId(), productId);
    }
    @GetMapping("/ordini")
    public List<OrderCustomerPayload> getOrdersByCustomerId(@AuthenticationPrincipal Customer currentAuthenticatedUser) {
        UUID customerId = currentAuthenticatedUser.getId();
        return orderProductService.getOrdersByCustomer(customerId);
    }

    @GetMapping("/cart")
    public List<CartResponsePayload> getCarts(@AuthenticationPrincipal Customer currentAuthenticatedUser) {
        return cartService.getActiveCarts(currentAuthenticatedUser.getId());
    }
    }



