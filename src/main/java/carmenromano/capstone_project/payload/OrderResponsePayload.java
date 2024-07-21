package carmenromano.capstone_project.payload;

import java.util.UUID;

public record OrderResponsePayload(String message, UUID cartOrOrderId) {
}
