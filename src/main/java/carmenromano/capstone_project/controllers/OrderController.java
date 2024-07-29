package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.OrderProduct;
import carmenromano.capstone_project.entities.Product;
import carmenromano.capstone_project.payload.OrderResponsePayload;
import carmenromano.capstone_project.services.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderProductService orderService;
    @PostMapping("/{cartId}")
    public OrderResponsePayload createOrder(@PathVariable UUID cartId, @AuthenticationPrincipal Customer currentAuthenticatedUser) {

        return orderService.createOrder(cartId, currentAuthenticatedUser);
    }
    @GetMapping
    public Page<OrderProduct> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.orderService.getOrders(page, size, sortBy);
    }

    @GetMapping("/sales/{year}")
    public double getTotalSalesForYear(@PathVariable int year) {
        return orderService.venditeTotaliPerAnno(year);
    }

}
