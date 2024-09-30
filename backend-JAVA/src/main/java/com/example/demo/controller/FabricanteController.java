package com.example.demo.controller;



import com.example.demo.entities.Fabricante;
import com.example.demo.service.FabricanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fabricantes")
@CrossOrigin
public class FabricanteController {

    @Autowired
    private final FabricanteService fabricanteService;

    public FabricanteController(FabricanteService fabricanteService) {
        this.fabricanteService = fabricanteService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Fabricante>> getAllFabricantes() {
        return ResponseEntity.ok(fabricanteService.findAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Fabricante> getFabricanteById(@PathVariable String id) {
        return fabricanteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Fabricante> createFabricante(@RequestBody Fabricante fabricante) {
        Fabricante nuevoFabricante = fabricanteService.save(fabricante);
        return ResponseEntity.ok(nuevoFabricante);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Fabricante> updateFabricante(@PathVariable String id, @RequestBody Fabricante fabricante) {
        return fabricanteService.findById(id)
                .map(fabricanteExistente -> {
                    fabricante.setId(id);
                    return ResponseEntity.ok(fabricanteService.save(fabricante));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFabricante(@PathVariable String id) {
        if (!fabricanteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        fabricanteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

