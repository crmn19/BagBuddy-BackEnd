package carmenromano.capstone_project.entities;

import carmenromano.capstone_project.enums.OrderStatus;
import carmenromano.capstone_project.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private double price;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> orderItems;

    @OneToOne

    @JoinColumn(name = "cart_id")
    private Cart cart;
}
