package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Factura;
import com.example.demo.requestBody.FacturaRequest;
import com.example.demo.service.FacturaService;

@CrossOrigin
@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarCompra(@RequestBody FacturaRequest facturaRequest) {
        try {
            Factura factura = facturaService.crearFactura(facturaRequest);
            byte[] pdf = facturaService.generarPdfFactura(factura);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdf);
        } catch (Exception e) {
            e.printStackTrace(); // Añade esto para imprimir la traza del error en los logs del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al confirmar la compra: " + e.getMessage());
        }
    }
}
