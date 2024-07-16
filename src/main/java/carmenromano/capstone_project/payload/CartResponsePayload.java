package carmenromano.capstone_project.payload;
import carmenromano.capstone_project.enums.CartStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CartResponsePayload(
        UUID id,
        LocalDate date,
        double price,
        CartStatus status,
        CustomerResponsePayload customer,
        List<OrderItemResponsePayload> products) {
}