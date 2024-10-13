package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Drogueria;
import com.example.demo.entities.Establecimiento;
import com.example.demo.service.EstablecimientoService;

@CrossOrigin
@RestController
@RequestMapping("/establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @GetMapping("/getAllEstablecimientos")
    public ResponseEntity<List<Establecimiento>> getAllEstablecimientos() {
        List<Establecimiento> establecimientos = establecimientoService.getAllEstablecimientos();
        return new ResponseEntity<>(establecimientos, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Establecimiento> getEstablecimientoById(@PathVariable Long id) {
        Establecimiento establecimiento = establecimientoService.getEstablecimientoById(id).get();
        if (establecimiento != null) {
            return new ResponseEntity<>(establecimiento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create_estab")
    public ResponseEntity<Establecimiento> createEstablecimiento(@RequestBody Establecimiento establecimiento) {
        Establecimiento newEstablecimiento = establecimientoService.createEstablecimiento(establecimiento);
        return new ResponseEntity<>(newEstablecimiento, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Establecimiento> updateEstablecimiento(@PathVariable Long id, @RequestBody Establecimiento establecimiento) {
        Establecimiento updatedEstablecimiento = establecimientoService.updateEstablecimiento(id, establecimiento);
        if (updatedEstablecimiento != null) {
            return new ResponseEntity<>(updatedEstablecimiento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getDrogueria/{id}")
    public Long getDrogueria(@PathVariable Long id){
        return establecimientoService.getDrogueria(id);
    }

}
