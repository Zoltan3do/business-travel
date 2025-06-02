package manu_barone.ViaggiAziendali.services;

import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.entities.Prenotazione;
import manu_barone.ViaggiAziendali.entities.Viaggio;
import manu_barone.ViaggiAziendali.exceptions.BadRequestException;
import manu_barone.ViaggiAziendali.exceptions.NotFoundException;
import manu_barone.ViaggiAziendali.payloads.DipendenteDTO;
import manu_barone.ViaggiAziendali.payloads.NotaDTO;
import manu_barone.ViaggiAziendali.payloads.PrenotazioneDTO;
import manu_barone.ViaggiAziendali.payloads.ViaggioDTO;
import manu_barone.ViaggiAziendali.repositories.DipendenteRepo;
import manu_barone.ViaggiAziendali.repositories.PrenotazioneRepo;
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
public class PrenotazioneSer {

    @Autowired
    private ViaggioSer viaggioRepo;

    @Autowired
    private DipendenteSer dipendenteRepo;

    @Autowired
    private PrenotazioneRepo prenotazioneRepo;

    public Prenotazione save(PrenotazioneDTO body) {
        Viaggio v = viaggioRepo.findById(body.id_viaggio());
        Dipendente d = dipendenteRepo.findById(body.id_dipendente());
        if (viaggioRepo.findByData(body.dataRichiesta()).isEmpty())
            throw new BadRequestException("Questa data non è disponibile");
        if (!this.findByDipendenteAndData(body.id_dipendente(), body.dataRichiesta()).isEmpty())
            throw new BadRequestException("Questa data già è occupata dal dipendente");
        Prenotazione p = new Prenotazione(
                v, d, body.dataRichiesta()
        );
        return this.prenotazioneRepo.save(p);
    }


    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepo.findAll(pageable);
    }


    public Prenotazione findById(UUID prenotazioneID) {
        return this.prenotazioneRepo.findById(prenotazioneID).orElseThrow(() -> new NotFoundException(prenotazioneID));
    }


    public void findByIdAndDelete(UUID id) {
        Prenotazione p = this.findById(id);
        this.prenotazioneRepo.delete(p);
    }


    public List<Prenotazione> findByDipendenteAndData(String user, LocalDate data) {
        return prenotazioneRepo.findByDipendente_UsernameAndViaggio_Data(user, data);
    }


    public Prenotazione aggiungiNota(UUID idPrenotazione, NotaDTO prenotazioneDTO){
        Prenotazione fund = this.findById(idPrenotazione);
        fund.setNote(prenotazioneDTO.nota());
        return this.prenotazioneRepo.save(fund);
    }

}
