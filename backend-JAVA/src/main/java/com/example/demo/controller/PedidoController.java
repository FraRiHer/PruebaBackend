package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Pedido;
import com.example.demo.requestBody.PedidoRequest;
import com.example.demo.service.PedidoService;

@CrossOrigin
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/proveedor/{codProv}")
    public ResponseEntity<List<Pedido>> getPedidosByProveedorAndEstablecimiento(
            @PathVariable Long codProv,
            @RequestParam Long establecimientoId) {
        List<Pedido> pedidos = pedidoService.findPedidosByProveedorAndEstablecimiento(codProv, establecimientoId);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/getByEstablecimiento/{establecimientoId}")
    public ResponseEntity<List<Pedido>> getPedidosByEstablecimiento (@PathVariable Long establecimientoId) {
        List<Pedido> pedidos = pedidoService.findByEstablecimiento(establecimientoId);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }
    

    // Endpoint para crear o actualizar un pedido
    @PostMapping("/crearPedido")
    public ResponseEntity<Pedido> createOrUpdatePedido(@RequestBody Pedido pedido,
            @RequestParam Long establecimientoId) {
        try {
            Pedido nuevoPedido = pedidoService.createOrUpdatePedido(pedido, establecimientoId);
            return ResponseEntity.ok(nuevoPedido);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null); // Puedes personalizar la respuesta en función del tipo de
                                                          // excepción
        }
    }

    @PostMapping("/finalizarPedido/{pedidoId}")
    public ResponseEntity<Pedido> finalizarPedido(@PathVariable Long pedidoId,
            @RequestBody List<PedidoRequest> productosRecibidos) {
        Pedido pedido = pedidoService.finalizarPedido(pedidoId, productosRecibidos);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/enEspera")
    public ResponseEntity<List<Pedido>> getPedidosEnEspera(@RequestParam Long establecimientoId) {
        List<Pedido> pedidos = pedidoService.findPedidosEnEsperaPorEstablecimiento(establecimientoId);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoService.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return ResponseEntity.ok(pedido);
    }

/*     @GetMapping("/listaPedidos")
    public ResponseEntity<List<Pedido>> listPedidos(@RequestParam Long establecimientoId) {
        List<Pedido> listPedios = pedidoService.getAllPedidos(establecimientoId);
        return ResponseEntity.ok(listPedios);
    } */
    

}
