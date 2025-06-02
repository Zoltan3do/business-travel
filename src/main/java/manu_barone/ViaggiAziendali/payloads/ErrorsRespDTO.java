package manu_barone.ViaggiAziendali.payloads;

import java.time.LocalDateTime;

public record ErrorsRespDTO(
        String message, LocalDateTime timestamp
        ) {
}
