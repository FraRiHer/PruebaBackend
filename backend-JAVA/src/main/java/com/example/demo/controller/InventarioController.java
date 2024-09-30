package com.example.demo.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.DetallePedido;
import com.example.demo.entities.Inventario;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Producto;
import com.example.demo.requestBody.PedidoRequest;
import com.example.demo.service.InventarioService;
import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;

@CrossOrigin
@RestController
@RequestMapping("/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @Autowired
    ProductoService productoService;

    @Autowired
    PedidoService pedidoService;

    /*
     * @PutMapping("/actualizarStock")
     * public ResponseEntity<String> actualizarStock(@RequestBody Inventario
     * inventario) {
     * try {
     * inventarioService.actualizarStock(inventario);
     * return
     * ResponseEntity.ok("{\"message\": \"Stock actualizado correctamente.\"}");
     * } catch (Exception e) {
     * return ResponseEntity.status(HttpStatus.NOT_FOUND).
     * body("Error al actualizar el stock: " + e.getMessage());
     * }
     * }
     */

    @GetMapping("/consulta/{codBarr}")
    public ResponseEntity<Inventario> consultarInventario(@PathVariable String codBarr,
            @RequestParam Long establecimientoId) {
        try {
            Optional<Inventario> inventario = inventarioService.consultarInventario(codBarr, establecimientoId);
            if (inventario.isPresent()) {
                return ResponseEntity.ok(inventario.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    @GetMapping("/consultaPorNombre/{nombre}")
    public ResponseEntity<Inventario> consultarInventarioPorNombre(@PathVariable String nombre,
            @RequestParam Long establecimientoId) {
        try {
            Optional<Inventario> inventario = inventarioService.consultarInventarioPorNombre(nombre, establecimientoId);
            if (inventario.isPresent()) {
                return ResponseEntity.ok(inventario.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/cargaMasiva")
    public ResponseEntity<Map<String, Object>> cargaMasiva(@RequestBody List<PedidoRequest> pedidos,
            @RequestParam Long establecimientoId) {
        int created = 0;
        int updated = 0;
        int errors = 0;
        List<String> createdProducts = new ArrayList<>();
        List<String> updatedProducts = new ArrayList<>();
        List<String> errorDetails = new ArrayList<>();

        // Crear el pedido
        Pedido pedidoInfo = new Pedido();
        pedidoInfo.setNumeroFactura(pedidos.get(0).getNumFactura());
        pedidoInfo.setFechaRegistro(LocalDate.now());
        pedidoInfo.setProveedor(pedidos.get(0).getProveedor());
        
        List<DetallePedido> detalles = new ArrayList<>();

        for (PedidoRequest pedido : pedidos) {
            try {

                if (!productoService.existeProducto(pedido.getInventario().getProducto())) {
                    Producto producto = productoService.saveOrUpdateProduct(pedido.getInventario().getProducto());
                    created++;
                    createdProducts.add(producto.getCodBarr() + " - " + producto.getNombre());
                    continue;
                }
                inventarioService.createOrUpdateInventory(pedido.getInventario(), establecimientoId);
                updated++;
                updatedProducts.add(
                        pedido.getInventario().getProducto().getCodBarr() + " - "
                                + pedido.getInventario().getProducto().getNombre());
                DetallePedido detalle = new DetallePedido();
                detalle.setProducto(pedido.getInventario().getProducto());
                detalle.setCantidad(pedido.getInventario().getCantidad());
                detalle.setPrecioCompra(pedido.getPrecioCompra());
                detalle.setPresentacion(pedido.getPresentacion());
                detalle.setTotal(pedido.getTotal());
                detalles.add(detalle);
            } catch (Exception e) {
                errors++;
                errorDetails.add(pedido.getInventario().getProducto().getCodBarr() + " - "
                        + pedido.getInventario().getProducto().getNombre() + ": " + e.getMessage());
            }
        }
        pedidoInfo.setDetalles(detalles); 
        pedidoService.createOrUpdatePedido(pedidoInfo,establecimientoId);

        Map<String, Object> result = new HashMap<>();
        result.put("created", created);
        result.put("updated", updated);
        result.put("errors", errors);
        result.put("createdProducts", createdProducts);
        result.put("updatedProducts", updatedProducts);
        result.put("errorDetails", errorDetails);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/createOrUpdate")
    public ResponseEntity<?> createInventario(@RequestParam Long establecimientoId,
            @RequestBody Inventario inventarioDTO) {
        try {
            inventarioService.createOrUpdateInventory(inventarioDTO, establecimientoId);
            return ResponseEntity.ok("{\"message\": \"Inventario creado o actualizado correctamente.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al crear o actualizar el inventario: " + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/actualizarStock")
    public ResponseEntity<String> actualizarStock(@RequestBody Inventario inventarioDTO,
            @RequestParam Long establecimientoId) {
        try {
            // Validación de datos
            if (inventarioDTO.getProducto().getCodBarr() == null || 
                inventarioDTO.getPrecioVenta() == null || 
                inventarioDTO.getPrecioVenta().compareTo(BigDecimal.ZERO) < 0) {
                return ResponseEntity.badRequest().body("{\"error\": \"Datos inválidos para la actualización.\"}");
            }

            inventarioService.createOrUpdateInventory(inventarioDTO, establecimientoId);
            return ResponseEntity.ok("{\"message\": \"Inventario actualizado correctamente.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al actualizar el inventario: " + e.getMessage() + "\"}");
        }
    }



    @GetMapping("/getProductos")
    public ResponseEntity<List<Inventario>> getInventario(@RequestParam Long establecimientoId) {
        return ResponseEntity.ok(inventarioService.findAllInventoryByEstablecimiento(establecimientoId));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Inventario>> buscarInventarios(@RequestParam String filtro,
            @RequestParam Long establecimientoId) {
        try {
            List<Inventario> inventarios = inventarioService.busquedaDinamica(filtro, establecimientoId);
            return ResponseEntity.ok(inventarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
