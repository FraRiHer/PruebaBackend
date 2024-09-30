package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.DrogueriaCliente;

@Repository
public interface DrogueriaClienteRepository extends JpaRepository<DrogueriaCliente,Integer> {
    List<DrogueriaCliente> findByDrogueriaId(Long drogueria_id);
}
