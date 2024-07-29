package carmenromano.capstone_project.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record OrderProductPaypalPayload(
        Double amount,
        String currency,
        String description,
        String intent,
        String method,
        String orderId
) {
}
