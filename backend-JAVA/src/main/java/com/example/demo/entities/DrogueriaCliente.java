package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Drogueria_Cliente")

public class DrogueriaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER
    @JoinColumn(name = "id_drogueria", nullable = false)
    @JsonIgnore
    private Drogueria drogueria;

    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    // Getter y Setter para id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter y Setter para drogueria
    public Drogueria getDrogueria() {
        return drogueria;
    }

    public void setDrogueria(Drogueria drogueria) {
        this.drogueria = drogueria;
    }

    // Getter y Setter para cliente
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

