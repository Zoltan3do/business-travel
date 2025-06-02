package manu_barone.ViaggiAziendali.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import manu_barone.ViaggiAziendali.entities.enums.StatoViaggio;

import java.time.LocalDate;
import java.util.UUID;

public record ViaggioDTO(
        @NotEmpty
        String destinazione,

        @NotNull
        @Future
        LocalDate data,

        @Pattern(regexp = "^(IN_PROGRAMMA|COMPLETATO)$")
        StatoViaggio sv
) {
}
