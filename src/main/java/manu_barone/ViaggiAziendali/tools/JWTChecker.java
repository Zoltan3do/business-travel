package manu_barone.ViaggiAziendali.tools;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.exceptions.UnothorizedException;
import manu_barone.ViaggiAziendali.services.DipendenteSer;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTChecker extends OncePerRequestFilter {

    @Autowired
    private JWT jwt;

    @Autowired
    private DipendenteSer dipendenteSer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new UnothorizedException("Inserire token nell' Authorization Header nel formato corretto !");
        String accessToken = authorizationHeader.split(" ")[1];
        jwt.verifyToken(accessToken);

        // cerco l'utente attuale basandosi sull'id estrapolato dal token
        String idUtente = jwt.getIdFromToken(accessToken);
        Dipendente utenteCorrente = this.dipendenteSer.findById(idUtente);

        // assegno all'utente le sue autorizzazioni
        Authentication authentication = new UsernamePasswordAuthenticationToken(utenteCorrente,null, utenteCorrente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
