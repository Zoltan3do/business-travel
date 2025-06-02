package manu_barone.ViaggiAziendali.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import manu_barone.ViaggiAziendali.entities.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "dipendenti")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"password","role","enabled","accountNonLocked","credentialsNonExpired","accountNonExpired","authorities"})
public class Dipendente implements UserDetails {
    @Id
    @Column(unique = true)
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String avatar;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    public Dipendente(String username, String nome, String cognome, String email, String password) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.avatar = "https://img.freepik.com/premium-vector/man-empty-avatar-casual-business-style-vector-photo-placeholder-social-networks-resumes_885953-434.jpg";
        this.password = password;
        this.role = Role.USER;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
}
