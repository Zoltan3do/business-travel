package manu_barone.ViaggiAziendali.repositories;

import manu_barone.ViaggiAziendali.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DipendenteRepo extends JpaRepository<Dipendente, String> {

}
