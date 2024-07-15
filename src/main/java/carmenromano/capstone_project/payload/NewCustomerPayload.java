package carmenromano.capstone_project.payload;

import carmenromano.capstone_project.enums.GenderUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewCustomerPayload(
        @NotEmpty(message = "Il campo Nome è obbligatorio!")
        @Size(min = 3, max = 40, message = "Nome deve essere compreso fra 3 e 40 caratteri")
        String nome,

        @NotEmpty(message = "Il campo Cognome è obbligatorio!")
        @Size(min = 3, max = 40, message = "Cognome deve essere compreso fra 3 e 40 caratteri")
        String cognome,

        @NotEmpty(message = "Il campo Email è obbligatorio!")
        @Email(message = "Formato email non valido")
        String email,

        @NotEmpty(message = "Il campo Password è obbligatorio!")
        @Size(min = 5, max = 15, message = "La password deve essere compresa tra 5 e 15 caratteri")
        String password,

        @NotNull(message = "Il campo Data di nascita è obbligatorio!")
        LocalDate dataDiNascita,

        @NotNull(message = "Il campo Sesso è obbligatorio!")
        GenderUser sesso


) {
}
