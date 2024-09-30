package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.DrogueriaCliente;
import com.example.demo.service.DrogueriaClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin
public class DrogueriaClienteController {
    @Autowired
    private DrogueriaClienteService drogueriaClienteService;

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<DrogueriaCliente>> getAll(){
        return ResponseEntity.ok(drogueriaClienteService.findAll());
    }

    @PostMapping(value = "/save")
    public DrogueriaCliente save(@RequestBody DrogueriaCliente drogueriaCliente){
        return drogueriaClienteService.save(drogueriaCliente);
    }

    @GetMapping(value = "/getByDrogueria/{drogueria_id}")
    public ResponseEntity<List<DrogueriaCliente>> getByDrogueriaId(@PathVariable Long drogueria_id){
        return ResponseEntity.ok(drogueriaClienteService.findByDrogueria(drogueria_id));
    }

}