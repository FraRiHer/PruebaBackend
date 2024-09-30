package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    List<Factura> findByEstablecimientoId(Long establecimientoId);
    List<Factura> findByEstablecimientoIdAndFechaBetween(Long establecimientoId, LocalDate startDate, LocalDate endDate);
    List<Factura> findByEstablecimientoIdAndClienteId(Long establecimientoId, Integer clienteId);
}
