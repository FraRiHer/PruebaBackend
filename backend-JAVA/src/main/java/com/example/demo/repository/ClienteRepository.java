package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Encuentra un cliente por su nombre
    Cliente findByNombre(String nombre);
    Optional<Cliente> findByCedula(Integer cedula);
    List<Cliente> findAllByOrderByNombreAsc();

}
