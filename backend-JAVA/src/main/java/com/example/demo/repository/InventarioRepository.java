package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario,Long>{
    Optional<Inventario> findByProductoCodBarrAndEstablecimientoId(String codBarr, Long establecimientoId);
    Optional<Inventario> findByProductoCodProductoAndEstablecimientoId(Long codProducto, Long establecimientoId);
    Optional<Inventario> findByProductoNombreAndEstablecimientoId(String nombre, Long establecimientoId);
    List<Inventario> findByProductoNombreContainingIgnoreCaseAndEstablecimientoId(String nombre, Long establecimientoId);
    List<Inventario> findByEstablecimientoId(Long establecimientoId);
}

