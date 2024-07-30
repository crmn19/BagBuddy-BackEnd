package carmenromano.capstone_project.services;

import carmenromano.capstone_project.payload.CartResponsePayload;
import carmenromano.capstone_project.payload.CustomerResponsePayload;
import carmenromano.capstone_project.payload.OrderItemResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import carmenromano.capstone_project.entities.*;
import carmenromano.capstone_project.enums.CartStatus;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.repositories.CartRepository;
import carmenromano.capstone_project.repositories.CustomerRepository;
import carmenromano.capstone_project.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartResponsePayload> aggiungiAlCarrello(UUID customerId, UUID productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con ID: " + customerId));

        // Recupero tutti i carrelli attivi per il cliente
        List<Cart> carts = cartRepository.findByCustomerAndStatus(customer, CartStatus.ACTIVE);
        // Se non ci sono carrelli attivi, creo un nuovo carrello
        if (carts.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setDate(LocalDate.now());
            newCart.setStatus(CartStatus.ACTIVE);
            carts.add(cartRepository.save(newCart));
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Prodotto non trovato con ID: " + productId));

        for (Cart cart : carts) {
            OrderItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseGet(() -> {
                        OrderItem newItem = new OrderItem();
                        newItem.setCart(cart);
                        newItem.setProduct(product);
                        newItem.setQuantity(0);
                        return newItem;
                    });

            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cart.getCartItems().add(cartItem);

            double totalPrice = cart.getCartItems().stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();
            cart.setPrice(totalPrice);
            cartRepository.save(cart);
        }

        // Creo una lista di risposte per tutti i carrelli attivi
        List<CartResponsePayload> cartResponsePayloads = carts.stream()
                .map(cart -> {
                    List<OrderItemResponsePayload> orderItemResponses = cart.getCartItems().stream()
                            .map(item -> new OrderItemResponsePayload(
                                    item.getProduct().getId(),
                                    item.getProduct().getName(),
                                    item.getProduct().getBrand(),
                                    item.getProduct().getDescription(),
                                    item.getProduct().getPrice(),
                                    item.getQuantity(),
                                    item.getProduct().getInMagazzino(),
                                    item.getProduct().getImageUrl()
                            ))
                            .collect(Collectors.toList());

                    return new CartResponsePayload(
                            cart.getId(),
                            cart.getDate(),
                            cart.getPrice(),
                            cart.getStatus(),
                            new CustomerResponsePayload(customer.getId()),
                            orderItemResponses
                    );
                })
                .collect(Collectors.toList());

        return cartResponsePayloads;
    }

    public List<CartResponsePayload> rimuoviDalCarrello(UUID customerId, UUID productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con ID: " + customerId));

        List<Cart> carts = cartRepository.findByCustomerAndStatus(customer, CartStatus.ACTIVE);

        if (carts.isEmpty()) {
            throw new NotFoundException("Nessun carrello attivo trovato per il cliente con ID: " + customerId);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Prodotto non trovato con ID: " + productId));

        for (Cart cart : carts) {
            OrderItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Prodotto non trovato nel carrello"));

            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            } else {
                cart.getCartItems().remove(cartItem);
            }

            double totalPrice = cart.getCartItems().stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();
            cart.setPrice(totalPrice);
            if (cart.getCartItems().isEmpty()) {
                cartRepository.delete(cart);
            } else {
                cartRepository.save(cart);
            }
        }




        List<CartResponsePayload> cartResponsePayloads = carts.stream()
                .map(cart -> {
                    List<OrderItemResponsePayload> orderItemResponses = cart.getCartItems().stream()
                            .map(item -> new OrderItemResponsePayload(
                                    item.getProduct().getId(),
                                    item.getProduct().getName(),
                                    item.getProduct().getBrand(),
                                    item.getProduct().getDescription(),
                                    item.getProduct().getPrice(),
                                    item.getQuantity(),
                                    item.getProduct().getInMagazzino(),
                                    item.getProduct().getImageUrl()
                            ))
                            .collect(Collectors.toList());

                    return new CartResponsePayload(
                            cart.getId(),
                            cart.getDate(),
                            cart.getPrice(),
                            cart.getStatus(),
                            new CustomerResponsePayload(customer.getId()),
                            orderItemResponses
                    );
                })
                .collect(Collectors.toList());

        return cartResponsePayloads;
    }
    public List<CartResponsePayload> getActiveCarts(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con ID: " + customerId));

        List<Cart> carts = cartRepository.findByCustomerAndStatus(customer, CartStatus.ACTIVE);

        if (carts.isEmpty()) {
            throw new NotFoundException("Nessun carrello attivo trovato per il cliente con ID: " + customerId);
        }

        return carts.stream()
                .map(cart -> {
                    List<OrderItemResponsePayload> orderItemResponses = cart.getCartItems().stream()
                            .map(item -> new OrderItemResponsePayload(
                                    item.getProduct().getId(),
                                    item.getProduct().getName(),
                                    item.getProduct().getBrand(),
                                    item.getProduct().getDescription(),
                                    item.getProduct().getPrice(),
                                    item.getQuantity(),
                                    item.getProduct().getInMagazzino(),
                                    item.getProduct().getImageUrl()
                            ))
                            .collect(Collectors.toList());

                    return new CartResponsePayload(
                            cart.getId(),
                            cart.getDate(),
                            cart.getPrice(),
                            cart.getStatus(),
                            new CustomerResponsePayload(customer.getId()),
                            orderItemResponses
                    );
                })
                .collect(Collectors.toList());
    }
}
