package carmenromano.capstone_project.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record PasswordResetPayload(
        @NotEmpty(message = "Il campo old password è obbligatorio!")
        String oldPassword,

        @NotEmpty(message = "Il campo Password è obbligatorio!")
        @Size(min = 5, max = 15, message = "La password deve essere compresa tra 5 e 15 caratteri")
        String newPassword
) {}
