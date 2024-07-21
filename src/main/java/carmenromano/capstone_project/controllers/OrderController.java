package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.OrderProduct;
import carmenromano.capstone_project.payload.OrderResponsePayload;
import carmenromano.capstone_project.services.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

}
