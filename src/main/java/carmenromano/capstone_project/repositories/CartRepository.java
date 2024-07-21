package carmenromano.capstone_project.repositories;

import carmenromano.capstone_project.entities.Cart;
import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCustomerAndStatus(Customer customer, CartStatus status);
}

