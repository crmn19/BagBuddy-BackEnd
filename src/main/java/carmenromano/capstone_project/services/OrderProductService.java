package carmenromano.capstone_project.services;


import carmenromano.capstone_project.entities.Cart;
import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.OrderProduct;
import carmenromano.capstone_project.enums.CartStatus;
import carmenromano.capstone_project.enums.OrderStatus;
import carmenromano.capstone_project.payload.OrderCustomerPayload;
import carmenromano.capstone_project.payload.OrderItemResponsePayload;
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
import carmenromano.capstone_project.entities.Cart;
import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.OrderItem;
import carmenromano.capstone_project.entities.OrderProduct;
import carmenromano.capstone_project.enums.CartStatus;
import carmenromano.capstone_project.enums.OrderStatus;
import carmenromano.capstone_project.payload.OrderResponsePayload;
import carmenromano.capstone_project.repositories.CartRepository;
import carmenromano.capstone_project.repositories.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        OrderProduct order = new OrderProduct();
        order.setCustomer(customer);
        order.setCreatedAt(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setCart(cart);
        List<OrderItem> orderItems = cart.getCartItems().stream().map(item -> {
            OrderItem newItem = new OrderItem();
            newItem.setProduct(item.getProduct());
            newItem.setQuantity(item.getQuantity());
            newItem.setOrder(order);
            return newItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        cart.setStatus(CartStatus.ORDERED);

        cartRepository.save(cart);
        OrderProduct savedOrder = orderProductRepository.save(order);

        return new OrderResponsePayload("Ordine creato con successo.", savedOrder.getId());
    }

    public List<OrderCustomerPayload> getOrdersByCustomer(UUID customerId) {
        List<OrderProduct> orders = orderProductRepository.findByCustomerId(customerId);

        return orders.stream().map(order -> {
            List<OrderItemResponsePayload> products = order.getOrderItems().stream()
                    .map(item -> new OrderItemResponsePayload(
                            item.getProduct().getId(),
                            item.getProduct().getName(),
                            item.getProduct().getBrand(),
                            item.getProduct().getDescription(),
                            item.getProduct().getPrice(),
                            item.getQuantity(),
                            item.getProduct().getInMagazzino(),
                            item.getProduct().getImageUrl()))
                    .collect(Collectors.toList());

            return new OrderCustomerPayload(order.getId(), order.getStatus(), products);
        }).collect(Collectors.toList());
    }
}
