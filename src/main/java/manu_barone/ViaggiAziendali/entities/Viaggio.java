package manu_barone.ViaggiAziendali.entities;


import jakarta.persistence.*;
import lombok.*;
import manu_barone.ViaggiAziendali.entities.enums.StatoViaggio;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Viaggio {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String destinazione;
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatoViaggio statoViaggio;

    public Viaggio(String destinazione, LocalDate data) {
        this.destinazione = destinazione;
        this.data = data;
        this.statoViaggio = StatoViaggio.IN_PROGRAMMA;
    }

}
