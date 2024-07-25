package carmenromano.capstone_project.repositories;

import carmenromano.capstone_project.entities.Cart;
import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query("SELECT c FROM Cart c WHERE c.customer = :customer AND c.status = :status")
    List<Cart> findByCustomerAndStatus(@Param("customer") Customer customer, @Param("status") CartStatus status);
}

