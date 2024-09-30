package com.example.demo.requestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
public class FacturaRequest {
    private Long clienteId;
    private Integer empleadoId;
    private Integer metodoPagoId;
    private Long establecimientoId;
    private BigDecimal totalVenta;
    private List<DetalleFacturaRequest> detalles = new ArrayList<>();
    
    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteID(Long clienteId) {
        this.clienteId = clienteId;
    }
    public Integer getEmpleadoId() {
        return empleadoId;
    }
    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }
    public Integer getMetodoPagoId() {
        return metodoPagoId;
    }
    public void setMetodoPagoId(Integer metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }
    public Long getEstablecimientoId() {
        return establecimientoId;
    }
    public void setEstablecimientoId(Long establecimientoId) {
        this.establecimientoId = establecimientoId;
    }
    public BigDecimal getTotalVenta() {
        return totalVenta;
    }
    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }
    public List<DetalleFacturaRequest> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleFacturaRequest> detalles) {
        this.detalles = detalles;
    }

    // Getters and Setters

    
}
