package manu_barone.ViaggiAziendali.services;

import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.entities.Viaggio;
import manu_barone.ViaggiAziendali.entities.enums.StatoViaggio;
import manu_barone.ViaggiAziendali.exceptions.BadRequestException;
import manu_barone.ViaggiAziendali.payloads.ViaggioDTO;
import manu_barone.ViaggiAziendali.repositories.ViaggioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ViaggioSer {

    @Autowired
    private ViaggioRepo vr;

    public Viaggio save(ViaggioDTO body) {
        if (!vr.findByDataAndDestinazione(body.data(), body.destinazione()).isEmpty())
            throw new BadRequestException("Esiste già un viaggio omonimo!!!");
        Viaggio v = new Viaggio(body.destinazione(), body.data());
        return this.vr.save(v);
    }


    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.vr.findAll(pageable);
    }

    public Viaggio findById(UUID viaggioId) {
        return this.vr.findById(viaggioId).orElseThrow(() -> new RuntimeException());
    }

    public void findByIdAndDelete(UUID id) {
        Viaggio p = this.findById(id);
        this.vr.delete(p);
    }

    public List<Viaggio> findByData(LocalDate ld) {
        return this.vr.findByData(ld);
    }

    public Viaggio changeState(UUID viaggioId) {
        Viaggio fund = this.findById(viaggioId);
        fund.setStatoViaggio(fund.getStatoViaggio() == StatoViaggio.COMPLETATO ? StatoViaggio.IN_PROGRAMMA : StatoViaggio.COMPLETATO);
        return this.vr.save(fund);
    }
}
