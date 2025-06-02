package manu_barone.ViaggiAziendali.repositories;

import manu_barone.ViaggiAziendali.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrenotazioneRepo extends JpaRepository<Prenotazione, UUID> {
    List<Prenotazione> findByDipendente_UsernameAndViaggio_Data(String username, LocalDate data);
}
