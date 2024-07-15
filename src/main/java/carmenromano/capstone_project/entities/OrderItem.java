package carmenromano.capstone_project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderProduct order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
