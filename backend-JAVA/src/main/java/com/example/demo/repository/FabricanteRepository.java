package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Fabricante;

public interface FabricanteRepository extends JpaRepository <Fabricante,String>{
    
}
