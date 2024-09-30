package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Drogueria;
import com.example.demo.entities.Establecimiento;
import com.example.demo.repository.EstablecimientoRepository;

@Service
public class EstablecimientoService {
    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    public List<Establecimiento> getAllEstablecimientos() {
        return establecimientoRepository.findAll();
    }

    public Optional<Establecimiento> getEstablecimientoById(Long id) {
        return establecimientoRepository.findById(id);
    }

    @Transactional
    public Establecimiento createEstablecimiento(Establecimiento establecimiento) {
        return establecimientoRepository.save(establecimiento);
    }

    @Transactional
    public Establecimiento updateEstablecimiento(Long id, Establecimiento establecimientoDetails) {
        Establecimiento establecimiento = establecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Establecimiento not found"));

        establecimiento.setNombre(establecimientoDetails.getNombre());
        establecimiento.setDireccion(establecimientoDetails.getDireccion());
        // Otros campos

        return establecimientoRepository.save(establecimiento);
    }

    @Transactional
    public void deleteEstablecimiento(Long id) {
        establecimientoRepository.deleteById(id);
    }

    @Transactional
    public Drogueria getDrogueria(Long id){
        Establecimiento establecimiento = establecimientoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Establecimiento not found"));
        return establecimiento.getDrogueria();
    }
}

