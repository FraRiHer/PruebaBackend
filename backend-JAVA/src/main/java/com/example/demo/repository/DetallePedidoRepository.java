package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    // Métodos de consulta adicionales si es necesario
}
