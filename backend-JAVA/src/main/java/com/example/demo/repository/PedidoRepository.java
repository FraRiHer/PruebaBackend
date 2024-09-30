package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Métodos de consulta adicionales si es necesario
    @Query("SELECT p FROM Pedido p JOIN FETCH p.detalles WHERE p.proveedor.codProv = :codProv AND p.establecimiento.id = :establecimientoId")
    List<Pedido> findPedidosByProveedorAndEstablecimiento(@Param("codProv") Long codProv, @Param("establecimientoId") Long establecimientoId);
    List<Pedido> findByEstablecimientoIdAndStatus(Long establecimientoId, String status);
    List<Pedido> findByEstablecimientoId(Long establecimientoId);
    Optional<Pedido> findByNumeroFactura(String numeroFactura);
}
