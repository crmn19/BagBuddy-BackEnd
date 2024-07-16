package carmenromano.capstone_project.payload;

import java.util.UUID;

public record ProductResponsePayload(
        UUID id,
        String name,
        String brand,
        String description,
        double price,
        int inStock,
        String imageUrl) {
}
