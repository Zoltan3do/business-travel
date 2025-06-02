package manu_barone.ViaggiAziendali.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotaDTO(
        @NotNull(message = "nota nulla")
        @NotEmpty(message = "nota vuota")
        @Size(min = 5, message = "Nota troppo breve")
        String nota
) {
}
