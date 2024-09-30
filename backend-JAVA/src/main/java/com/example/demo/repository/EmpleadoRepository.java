package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Integer>{
    List<Empleado> findByEstablecimientoId(Long establecimiento_id);
}
