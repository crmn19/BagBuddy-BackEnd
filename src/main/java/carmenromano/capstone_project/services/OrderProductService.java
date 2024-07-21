package carmenromano.capstone_project.services;


import carmenromano.capstone_project.entities.Cart;
import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.OrderProduct;
import carmenromano.capstone_project.enums.CartStatus;
import carmenromano.capstone_project.enums.OrderStatus;
import carmenromano.capstone_project.payload.OrderResponsePayload;
import carmenromano.capstone_project.repositories.CartRepository;
import carmenromano.capstone_project.repositories.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private CartRepository cartRepository;

    public List<OrderProduct> getAllOrders() {
        return orderProductRepository.findAll();
    }
    public OrderResponsePayload createOrder(UUID cartId, Customer customer) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrello non trovato"));

        if (cart.getStatus() == CartStatus.ORDERED) {
            Optional<OrderProduct> existingOrder = orderProductRepository.findByCartId(cartId);
            if (existingOrder.isPresent()) {
                Cart newCart = new Cart();
                newCart.setCustomer(customer);
                newCart.setDate(LocalDate.now());
                newCart.setStatus(CartStatus.ACTIVE);
                newCart = cartRepository.save(newCart);
                return new OrderResponsePayload("Il carrello è già stato ordinato. Un nuovo carrello è stato creato.", newCart.getId());
            }
        }

        // Crea il nuovo ordine
        OrderProduct order = new OrderProduct();
        order.setCustomer(customer);
        order.setCreatedAt(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setCart(cart);
        cart.setStatus(CartStatus.ORDERED);
        cartRepository.save(cart);
        OrderProduct savedOrder = orderProductRepository.save(order);

        return new OrderResponsePayload("Ordine creato con successo.", savedOrder.getId());
    }


    public List<OrderProduct> getOrdersByCustomer(UUID customerId) {
        return orderProductRepository.findByCustomerId(customerId);
    }
}
