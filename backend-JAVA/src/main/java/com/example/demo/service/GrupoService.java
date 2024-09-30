package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Grupo;
import com.example.demo.repository.GrupoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private final GrupoRepository GrupoRepository;

    public GrupoService(GrupoRepository GrupoRepository) {
        this.GrupoRepository = GrupoRepository;
    }

    public List<Grupo> findAll() {
        return GrupoRepository.findAll();
    }

    public Optional<Grupo> findById(String id) {
        return GrupoRepository.findById(id);
    }

    public Grupo save(Grupo Grupo) {
        return GrupoRepository.save(Grupo);
    }

    public void deleteById(String id) {
        GrupoRepository.deleteById(id);
    }
}
