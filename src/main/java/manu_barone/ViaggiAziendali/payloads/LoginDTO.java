package manu_barone.ViaggiAziendali.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginDTO(
        @NotEmpty
        @NotNull
        String username,

        @NotEmpty
        @NotNull
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "La password non segue i criteri comuni")
        String password
) {
}
