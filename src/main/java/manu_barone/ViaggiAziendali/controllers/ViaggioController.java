package manu_barone.ViaggiAziendali.controllers;

import manu_barone.ViaggiAziendali.entities.Viaggio;
import manu_barone.ViaggiAziendali.exceptions.BadRequestException;
import manu_barone.ViaggiAziendali.payloads.ViaggioDTO;
import manu_barone.ViaggiAziendali.services.ViaggioSer;
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
@RequestMapping("/viaggi")
public class ViaggioController {

    @Autowired
    private ViaggioSer viaggioSer;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Viaggio> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        return this.viaggioSer.findAll(page, size, sortBy);
    }

    @GetMapping("/{viaggioId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Viaggio findById(@PathVariable UUID viaggioId) {
        return this.viaggioSer.findById(viaggioId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio save(@RequestBody @Validated ViaggioDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.viaggioSer.save(body);
    }

    @DeleteMapping("/{viaggioId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID viaggioId) {
        this.viaggioSer.findByIdAndDelete(viaggioId);
    }

    @PatchMapping("/{viaggioId}/changeState")
    public Viaggio changeState(@PathVariable("viaggioId") UUID viaggioId) {
        return this.viaggioSer.changeState(viaggioId);
    }


}
