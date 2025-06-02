package manu_barone.ViaggiAziendali.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.exceptions.BadRequestException;
import manu_barone.ViaggiAziendali.payloads.DipendenteDTO;
import manu_barone.ViaggiAziendali.payloads.LoginDTO;
import manu_barone.ViaggiAziendali.payloads.LoginResponseDTO;
import manu_barone.ViaggiAziendali.services.DipendenteSer;
import manu_barone.ViaggiAziendali.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")

public class AuthorizationController {

    @Autowired
    private SecurityService ss;

    @Autowired
    private DipendenteSer dipendenteSer;

    @PostMapping("/login")
    public LoginResponseDTO LoginResponseDTO(@RequestBody @Validated LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return new LoginResponseDTO(this.ss.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.dipendenteSer.save(body);
    }
}
