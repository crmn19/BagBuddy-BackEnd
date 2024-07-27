package carmenromano.capstone_project.payload;

import carmenromano.capstone_project.enums.CategoryProduct;

import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductPayload (
        @NotEmpty(message = "Il campo Nome è obbligatorio!")
        @Size(min = 3, max = 40, message = "Nome deve essere compreso fra 3 e 40 caratteri")
        String nome,

        @NotEmpty(message = "Il campo Descrizione è obbligatorio!")
        @Size(min = 3, max = 255, message = "Descrizione deve essere compresa fra 3 e 255 caratteri")
        String description,

        @NotEmpty(message = "Il campo Brand è obbligatorio!")
        @Size(min = 3, max = 255, message = "Brand deve essere compresa fra 3 e 20 caratteri")
        String brand,

        @NotNull(message = "Il campo Prezzo è obbligatorio!")
        int price,

        @NotNull(message = "Il campo Magazzino è obbligatorio!")
        int inMagazzino,

        @NotNull(message = "La categoria del prodotto è obbligatoria!")
        @Enumerated(EnumType.STRING)
        CategoryProduct categoryProduct,

          @Nullable
int priceDiscount
) {}
