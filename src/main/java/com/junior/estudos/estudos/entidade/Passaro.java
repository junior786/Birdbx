package com.junior.estudos.estudos.entidade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Passaro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotEmpty
    private String raca;
    @Column
    private String sexo;
    @ManyToOne
    private Gaiola gaiola;
    @ManyToOne
    private Usuario usuario;
    @Column
    private String identificacao;

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Gaiola getGaiola() {
        return gaiola;
    }

    public void setGaiola(Gaiola gaiola) {
        this.gaiola = gaiola;
    }


}
