package manu_barone.ViaggiAziendali.controllers;

import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.entities.Viaggio;
import manu_barone.ViaggiAziendali.exceptions.BadRequestException;
import manu_barone.ViaggiAziendali.payloads.DipendenteDTO;
import manu_barone.ViaggiAziendali.services.DipendenteSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteSer dipendenteSer;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "username") String sortBy) {
        return this.dipendenteSer.findAll(page, size, sortBy);
    }

    @GetMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public Dipendente findById(@PathVariable String dipendenteId){
        return this.dipendenteSer.findById(dipendenteId);
    }

    @PatchMapping("/{dipendenteId}/avatar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addAvatar(@PathVariable("dipendenteId") String dipendenteId, @RequestParam("avatar") MultipartFile file){
        return this.dipendenteSer.aggiungiAvatar(dipendenteId, file);
    }

    @PutMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente findByIdAndUpdate(@PathVariable String dipendenteId, @RequestBody @Validated DipendenteDTO body, BindingResult br){
        if(br.hasErrors()){
            br.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Errori nel payload");
        }
        return this.dipendenteSer.findByIdAndUpdate(dipendenteId,body);
    }


    // ************************ ME ENDPOINTS **************************

    @GetMapping("/me")
    public Dipendente getProfile(@AuthenticationPrincipal Dipendente current){return current;}

    @PutMapping("/me")
    public Dipendente updateProfile(@AuthenticationPrincipal Dipendente current,@RequestBody @Validated DipendenteDTO body) {
        return this.dipendenteSer.findByIdAndUpdate(current.getUsername(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Dipendente current){
        this.dipendenteSer.findByIdAndDelete(current.getUsername());
    }

    @PatchMapping("/me/avatar")
    public String addAvatar(@AuthenticationPrincipal Dipendente current, @RequestParam("avatar") MultipartFile file){
        return this.dipendenteSer.aggiungiAvatar(current.getUsername(), file);
    }



}
