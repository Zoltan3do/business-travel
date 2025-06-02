package manu_barone.ViaggiAziendali.controllers;

import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.entities.Prenotazione;
import manu_barone.ViaggiAziendali.entities.Viaggio;
import manu_barone.ViaggiAziendali.exceptions.BadRequestException;
import manu_barone.ViaggiAziendali.payloads.DipendenteDTO;
import manu_barone.ViaggiAziendali.payloads.NotaDTO;
import manu_barone.ViaggiAziendali.payloads.PrenotazioneDTO;
import manu_barone.ViaggiAziendali.services.PrenotazioneSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneSer prenotazioneSer;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Prenotazione> findAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return this.prenotazioneSer.findAll(page, size, sortBy);
    }

    @GetMapping("/{prenotazioneId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prenotazione findById(@PathVariable UUID prenotazioneId){
        return this.prenotazioneSer.findById(prenotazioneId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione save(@RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.prenotazioneSer.save(body);
    }

    @PatchMapping("{prenotazioneId}/aggiungiNota")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Prenotazione aggiungiNota(@PathVariable UUID prenotazioneId, @RequestBody @Validated NotaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel body");
        }
        return this.prenotazioneSer.aggiungiNota(prenotazioneId, body);
    }
}
