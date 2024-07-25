package carmenromano.capstone_project.payload;

import carmenromano.capstone_project.entities.OrderProduct;
import carmenromano.capstone_project.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public record OrderCustomerPayload(UUID orderId,
                                   OrderStatus status,
                                   List<OrderItemResponsePayload> products) {
}
