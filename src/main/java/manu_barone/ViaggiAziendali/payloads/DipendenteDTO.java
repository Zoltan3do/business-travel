package manu_barone.ViaggiAziendali.payloads;

import jakarta.validation.constraints.*;

public record DipendenteDTO(
        @NotEmpty(message = "Lo username è obbligatorio!")
        @Size(min = 2, max = 40, message = "Lo username deve essere compreso tra 2 e 40 caratteri!")
        String username,
        @NotEmpty(message = "Lo username è obbligatorio!")
        @Size(min = 2, max = 40, message = "Il nome deve essere compreso tra 2 e 40 caratteri!")
        String nome,
        @NotEmpty(message = "Lo username è obbligatorio!")
        @Size(min = 2, max = 40, message = "Il cognome deve essere compreso tra 2 e 40 caratteri!")
        String cognome,
        @NotEmpty(message = "Lo username è obbligatorio!")
        @Email(message = "L'email inserita non è un'email valida!")
        @Size(min = 4, message = "La password deve avere almeno 4 caratteri!")
        String email,
        @NotEmpty(message = "password vuota")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "La password non segue i criteri comuni")
        String password
) {
}
