package com.junior.estudos.estudos.entidade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
public class Gaiola implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private int numero;
    @Column
    @NotEmpty
    private int numeroFilhotes;
    @Column(columnDefinition = "text")
    private String notacao;
    @Column
    @OneToMany(mappedBy = "gaiola",cascade = CascadeType.ALL)
    private List<Passaro> passaro;
    @ManyToOne
    private Usuario usuario;


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumeroFilhotes() {
        return numeroFilhotes;
    }

    public void setNumeroFilhotes(int numeroFilhotes) {
        this.numeroFilhotes = numeroFilhotes;
    }

    public String getNotacao() {
        return notacao;
    }

    public void setNotacao(String notacao) {
        this.notacao = notacao;
    }

    public List<Passaro> getPassaro() {
        return passaro;
    }

    public void setPassaro(List<Passaro> passaro) {
        this.passaro = passaro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return ""+this.getNumero();
    }
}
