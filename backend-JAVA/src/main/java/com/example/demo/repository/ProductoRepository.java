package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /* Optional<Producto> findByCodBarr(String codBarr);
    Optional<Producto> findByCodProducto(Long codProducto);
    List<Producto> findByNombreContainingIgnoreCase(String filtro); */
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR LOWER(p.codBarr) LIKE LOWER(CONCAT('%',:filtro, '%'))")
    List<Producto> searchByNombreOrCodBarr(@Param("filtro") String filtro);
    Optional<Producto> findByCodBarr(String codBarr);
    Optional<Producto> findByCodProducto(Long codProducto);
}
