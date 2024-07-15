package carmenromano.capstone_project.payload;

import jakarta.validation.constraints.NotEmpty;

public record CustomerLoginPayload(@NotEmpty(message = "Il campo Email è obbligatorio!")
                                   String email,
                                   @NotEmpty(message = "Il campo Password è obbligatorio!d")
                                   String password) {
}
