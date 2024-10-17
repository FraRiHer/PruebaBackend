package com.example.demo.requestBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FacturaResponse {
    private Long numFactura;
    private String nombreCliente;
    private String nombreEmpleado;
    private String metodoPago;
    private LocalDate fecha;
    private BigDecimal totalVenta;
    private String nombreDrogueria;
    private String direccionEstablecimiento;
    private List<DetalleFacturaResponse> detalles;
    
    public Long getNumFactura() {
        return numFactura;
    }
    public void setNumFactura(Long numFactura) {
        this.numFactura = numFactura;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }
    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public BigDecimal getTotalVenta() {
        return totalVenta;
    }
    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }
    public String getNombreDrogueria() {
        return nombreDrogueria;
    }
    public void setNombreDrogueria(String nombreDrogueria) {
        this.nombreDrogueria = nombreDrogueria;
    }
    public String getDireccionEstablecimiento() {
        return direccionEstablecimiento;
    }
    public void setDireccionEstablecimiento(String direccionEstablecimiento) {
        this.direccionEstablecimiento = direccionEstablecimiento;
    }
    public List<DetalleFacturaResponse> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleFacturaResponse> detalles) {
        this.detalles = detalles;
    }



}
