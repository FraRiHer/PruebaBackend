package com.example.demo.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_producto")
    private Producto producto;

    private int cantidad;
    private int canBliste;
    private int canUnidad; 
    private String ubicacion;
    private BigDecimal precioVenta;
    private BigDecimal precioVentaBlis;
    private BigDecimal precioVentaUnid;
    

    @ManyToOne
    @JoinColumn(name = "establecimiento_id")
    private Establecimiento establecimiento;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /* @JsonBackReference */
    public Producto getProducto() {
        return producto;
    }
    /* @JsonManagedReference */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCanBliste() {
        return canBliste;
    }

    public void setCanBliste(int canBliste) {
        this.canBliste = canBliste;
    }

    public int getcanUnidad() {
        return canUnidad;
    }

    public void setCanUnidad(int canUnidad) {
        this.canUnidad = canUnidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setPrecioVenta(BigDecimal precio){
        this.precioVenta = precio;
    }

    public BigDecimal getPrecioVenta(){
        return precioVenta;
    }

    public BigDecimal getPrecioVentaBlis() {
        return precioVentaBlis;
    }

    public void setPrecioVentaBlis(BigDecimal precioVentaBlis) {
        this.precioVentaBlis = precioVentaBlis;
    }

    public BigDecimal getPrecioVentaUnid() {
        return precioVentaUnid;
    }

    public void setPrecioVentaUnid(BigDecimal precioVentaUnid) {
        this.precioVentaUnid = precioVentaUnid;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @JsonBackReference
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }
}