package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.DetalleFactura;
import com.example.demo.entities.Factura;
import com.example.demo.entities.Producto;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {

    // Encuentra todos los detalles de factura asociados a una factura específica
    List<DetalleFactura> findByFactura(Factura factura);

    // Encuentra todos los detalles de factura asociados a un producto específico
    List<DetalleFactura> findByProducto(Producto producto);
}
