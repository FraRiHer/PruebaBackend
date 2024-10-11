package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Empleado;
import com.example.demo.service.EmpleadoService;

@RestController
@RequestMapping("/empleado")
@CrossOrigin
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping(value = "getAll")
    public ResponseEntity<List<Empleado>> getAllEmpleados(){
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/getByEstablecimientoId/{establecimientoId}")
    public List<Empleado> getByEstablecimiento(@PathVariable Long establecimientoId){
        return empleadoService.findByEstablecimientoId(establecimientoId);
    }
}
