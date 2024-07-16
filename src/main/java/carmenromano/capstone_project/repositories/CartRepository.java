package carmenromano.capstone_project.repositories;

import carmenromano.capstone_project.entities.Cart;
import carmenromano.capstone_project.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCustomer(Customer customerId);
}
