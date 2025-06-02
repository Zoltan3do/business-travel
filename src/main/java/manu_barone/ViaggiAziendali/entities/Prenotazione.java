package manu_barone.ViaggiAziendali.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="id_viaggio")
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name="id_dipendente")
    private Dipendente dipendente;

    private LocalDate dataRichiesta;
    private String note = "";

    public Prenotazione(Viaggio viaggio, Dipendente dipendente, LocalDate dataRichiesta, String note) {
        this.viaggio = viaggio;
        this.dipendente = dipendente;
        this.dataRichiesta = dataRichiesta;
        this.note = note;
    }

    public Prenotazione(Viaggio viaggio, Dipendente dipendente, LocalDate dataRichiesta) {
        this.viaggio = viaggio;
        this.dipendente = dipendente;
        this.dataRichiesta = dataRichiesta;
    }
}
