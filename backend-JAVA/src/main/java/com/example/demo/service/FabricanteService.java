package com.example.demo.service;

import com.example.demo.entities.Fabricante;
import com.example.demo.repository.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FabricanteService {

    @Autowired
    private final FabricanteRepository fabricanteRepository;

    public FabricanteService(FabricanteRepository fabricanteRepository) {
        this.fabricanteRepository = fabricanteRepository;
    }

    public List<Fabricante> findAll() {
        return fabricanteRepository.findAll();
    }

    public Optional<Fabricante> findById(String id) {
        return fabricanteRepository.findById(id);
    }

    public Fabricante save(Fabricante fabricante) {
        return fabricanteRepository.save(fabricante);
    }

    public void deleteById(String id) {
        fabricanteRepository.deleteById(id);
    }
}

