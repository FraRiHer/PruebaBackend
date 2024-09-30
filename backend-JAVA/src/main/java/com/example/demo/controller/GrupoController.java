package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Grupo;
import com.example.demo.service.GrupoService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Grupos")
public class GrupoController {

    @Autowired
    private  GrupoService GrupoService;
    
    @GetMapping("/getAllGrupos")
    public ResponseEntity<List<Grupo>> getAllGrupos() {
        return ResponseEntity.ok(GrupoService.findAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Grupo> getGrupoById(@PathVariable String id) {
        return GrupoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Grupo> createGrupo(@RequestBody Grupo Grupo) {
        Grupo nuevaGrupo = GrupoService.save(Grupo);
        return ResponseEntity.ok(nuevaGrupo);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable String id, @RequestBody Grupo Grupo) {
        return GrupoService.findById(id)
                .map(GrupoExistente -> {
                    Grupo.setCodGrupo(id);
                    return ResponseEntity.ok(GrupoService.save(Grupo));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGrupo(@PathVariable String id) {
        if (!GrupoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        GrupoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
