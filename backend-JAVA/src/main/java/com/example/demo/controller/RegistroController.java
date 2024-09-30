package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Auth.AuthResponse;
import com.example.demo.Auth.RegisterRequest;
import com.example.demo.entities.Empleado;
import com.example.demo.service.EmpleadoService;
import com.example.demo.service.RegistroService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin
@RestController
@RequestMapping("/registro")
public class RegistroController {
    @Autowired
    RegistroService registroService;
    @Autowired
    EmpleadoService empleadoService;

    @PostMapping("/add")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(registroService.register(request));
    }
    
    @PostMapping("/empleado")
    public ResponseEntity<Empleado> saveEmpleado(@RequestBody Empleado request) {
        return ResponseEntity.ok(empleadoService.save(request));
    }

    @GetMapping("/getAllEmpleados")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        return ResponseEntity.ok(empleadoService.findAll());
    }
    
    
    
}
