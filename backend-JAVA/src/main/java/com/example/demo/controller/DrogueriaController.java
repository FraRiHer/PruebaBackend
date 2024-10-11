package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Drogueria;
import com.example.demo.service.DrogueriaService;

import java.util.List;

@RestController
@RequestMapping("/droguerias")
@CrossOrigin
public class DrogueriaController {
    @Autowired
    private DrogueriaService drogueriaService;

    @GetMapping
    public List<Drogueria> getAllDroguerias() {
        return drogueriaService.getAllDroguerias();
    }

    @GetMapping("/search")
    public List<Drogueria> searchDroguerias(@RequestParam String nombre) {
        return drogueriaService.findDrogueriasByNombre(nombre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drogueria> getDrogueriaById(@PathVariable Long id) {
        return drogueriaService.getDrogueriaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Drogueria createDrogueria(@RequestBody Drogueria drogueria) {
        return drogueriaService.createDrogueria(drogueria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drogueria> updateDrogueria(@PathVariable Long id, @RequestBody Drogueria drogueriaDetails) {
        return ResponseEntity.ok(drogueriaService.updateDrogueria(id, drogueriaDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrogueria(@PathVariable Long id) {
        drogueriaService.deleteDrogueria(id);
        return ResponseEntity.noContent().build();
    }
}
