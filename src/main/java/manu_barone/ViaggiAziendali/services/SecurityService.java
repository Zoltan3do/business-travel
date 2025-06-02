package manu_barone.ViaggiAziendali.services;

import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.exceptions.UnothorizedException;
import manu_barone.ViaggiAziendali.payloads.LoginDTO;
import manu_barone.ViaggiAziendali.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private DipendenteSer dipendenteSer;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Dipendente found = this.dipendenteSer.findById(body.username());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwt.createToken(found);
        } else {
            throw new UnothorizedException("Credenziali errate!");
        }
    }
}
