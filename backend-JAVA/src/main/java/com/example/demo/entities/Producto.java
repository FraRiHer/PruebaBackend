package com.example.demo.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_generator")
    @SequenceGenerator(name = "secuencia_generator", sequenceName = "producto_secuencia", allocationSize = 1)
    private Long codProducto;

    private String codBarr;
    private String nombre;
    private BigDecimal iva;
    private String presentacion;
    private int limUnidadCj;
    private int limUniBlister;
    private int limBlisterCj;
    
    public int getLimBlisterCj() {
        return limBlisterCj;
    }
    public void setLimBlisterCj(int limBlisterCj) {
        this.limBlisterCj = limBlisterCj;
    }
    public int getLimUniBlister() {
        return limUniBlister;
    }
    public void setLimUniBlister(int limUniBlister) {
        this.limUniBlister = limUniBlister;
    }
    public int getLimUnidadCj() {
        return limUnidadCj;
    }
    public void setLimUnidadCj(int limUnidadCj) {
        this.limUnidadCj = limUnidadCj;
    }
    

    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    @JsonIgnoreProperties("productos")
    private Fabricante fabricante;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    // Getters y setters
    public Long getCodProducto() {
        return codProducto;
    }
    public void setCodProducto(Long codProducto) {
        this.codProducto = codProducto;
    }
    public String getCodBarr() {
        return codBarr;
    }
    public void setCodBarr(String codBarr) {
        this.codBarr = codBarr;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public BigDecimal getIva() {
        return iva;
    }
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }
    public String getPresentacion() {
        return presentacion;
    }
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }
    public Fabricante getFabricante() {
        return fabricante;
    }
    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
    public Grupo getGrupo() {
        return grupo;
    }
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}