package com.appeventos.criadorEventos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Convidado {
    @Id
    @NotEmpty(message = "esse campo não pode estar vazio")
    private String rg;
    @NotEmpty(message = "esse campo não pode estar vazio")
    private String nomeconvidado;
    @ManyToOne
    private Evento evento;

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNomeconvidado() {
        return nomeconvidado;
    }

    public void setNomeconvidado(String nomeConvidado) {
        this.nomeconvidado = nomeConvidado;
    }
}
