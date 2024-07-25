package carmenromano.capstone_project.entities;

import carmenromano.capstone_project.enums.OrderStatus;
import carmenromano.capstone_project.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
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

    private String currency;
    private String method;
    private String intent;
    private String description;



    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> orderItems;


    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", paymentMethod=" + paymentMethod +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", method='" + method + '\'' +
                ", intent='" + intent + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
