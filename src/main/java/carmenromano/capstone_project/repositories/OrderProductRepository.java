package carmenromano.capstone_project.repositories;

import carmenromano.capstone_project.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    Optional<OrderProduct> findByCartId(UUID cartId);
    List<OrderProduct> findByCustomerId(UUID customerId);
}
