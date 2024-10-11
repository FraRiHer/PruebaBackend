package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Drogueria;
import java.util.List; // Asegúrate de que esta línea esté presente


@Repository
public interface DrogueriaRepository extends JpaRepository<Drogueria, Long> {

    List<Drogueria> findByNombreContainingIgnoreCase(String nombre);
}
