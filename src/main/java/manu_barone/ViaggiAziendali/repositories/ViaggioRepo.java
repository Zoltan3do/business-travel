package manu_barone.ViaggiAziendali.repositories;

import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ViaggioRepo extends JpaRepository<Viaggio, UUID> {
    List<Viaggio> findByData(LocalDate ld);
    List<Viaggio> findByDataAndDestinazione(LocalDate data, String destinazione);
}
