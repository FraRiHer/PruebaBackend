package com.example.demo.requestBody;

import java.math.BigDecimal;

import com.example.demo.entities.Inventario;
import com.example.demo.entities.Proveedor;

public class PedidoRequest {
    private Inventario inventario;
    private BigDecimal precioCompra;
    private String presentacion;
    private BigDecimal total;
    private Proveedor proveedor;
    private String numFactura;

    public Inventario getInventario() {
        return inventario;
    }
    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }
    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }
    public String getPresentacion() {
        return presentacion;
    }
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public Proveedor getProveedor() {
        return proveedor;
    }
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    public String getNumFactura(){
        return numFactura;
    }
    public void setNumFactura(String numFactura){
        this.numFactura = numFactura;
    }
    

}
