package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Drogueria;
import com.example.demo.repository.DrogueriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DrogueriaService {

    @Autowired
    private DrogueriaRepository drogueriaRepository;

    // Otros métodos...

    // Método para buscar droguerías por nombre
    public List<Drogueria> findDrogueriasByNombre(String nombre) {
        return drogueriaRepository.findByNombreContainingIgnoreCase(nombre);
    }


    public List<Drogueria> getAllDroguerias() {
        return drogueriaRepository.findAll();
    }

    public Optional<Drogueria> getDrogueriaById(Long id) {
        return drogueriaRepository.findById(id);
    }

    @Transactional
    public Drogueria createDrogueria(Drogueria drogueria) {
        return drogueriaRepository.save(drogueria);
    }

    @Transactional
    public Drogueria updateDrogueria(Long id, Drogueria drogueriaDetails) {
        Drogueria drogueria = drogueriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drogueria not found"));

        drogueria.setNombre(drogueriaDetails.getNombre());
        drogueria.setDireccion(drogueriaDetails.getDireccion());
        drogueria.setNit(drogueriaDetails.getNit());
        // Otros campos

        return drogueriaRepository.save(drogueria);
    }

    @Transactional
    public void deleteDrogueria(Long id) {
        drogueriaRepository.deleteById(id);
    }
}

