package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Factura;
import com.example.demo.requestBody.FacturaRequest;
import com.example.demo.requestBody.FacturaResponse;
import com.example.demo.service.FacturaService;


@CrossOrigin
@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/confirmar")
    public ResponseEntity<FacturaResponse> confirmarCompra(@RequestBody FacturaRequest facturaRequest) {
        try {
            FacturaResponse facturaResponse = facturaService.crearFactura(facturaRequest);
            return ResponseEntity.ok(facturaResponse);
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la traza del error en los logs del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getByEstablecimiento/{establecimientoId}")
    public ResponseEntity<List<Factura>> getFacturaByEstablecimiento (@PathVariable Long establecimientoId) {
        List<Factura> facturas = facturaService.getByEstablecimiento(establecimientoId);
        if(facturas.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(facturas);
        }   
    }
    
}
