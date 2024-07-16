package carmenromano.capstone_project.payload;


import java.util.UUID;
public record OrderItemResponsePayload(
        UUID productId,
        String name,
        String brand,
        String description,
        double price,
        int quantity,
        int inStock,
        String imageUrl
) {

}
