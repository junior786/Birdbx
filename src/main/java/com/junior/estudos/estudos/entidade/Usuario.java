package com.junior.estudos.estudos.entidade;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;


@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String nome;
    @Column(unique = true)
    private String nomeCriatorio;
    @Email
    @Column(unique = true)
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Passaro> passaro;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Gaiola> gaiola;

    @ManyToMany
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(
                    name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "nomeRole"))
    private List<Roletb> roles;


    public List<Roletb> getRoles() {
        return roles;
    }

    public void setRoles(List<Roletb> roles) {
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCriatorio() {
        return nomeCriatorio;
    }

    public void setNomeCriatorio(String nomeCriatorio) {
        this.nomeCriatorio = nomeCriatorio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) this.roles;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Passaro> getPassaro() {
        return passaro;
    }

    public void setPassaro(List<Passaro> passaro) {
        this.passaro = passaro;
    }

    public List<Gaiola> getGaiola() {
        return gaiola;
    }

    public void setGaiola(List<Gaiola> gaiola) {
        this.gaiola = gaiola;
    }
}
