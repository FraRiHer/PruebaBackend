package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Producto;
import com.example.demo.service.ProductoService;
import java.util.List;


@RestController
@RequestMapping("/productos")
@CrossOrigin
public class ProductoController {

    @Autowired
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<List<Producto>> getAllProductos(@RequestParam Long establecimientoId) {
        return ResponseEntity.ok(productoService.findAll(establecimientoId));
    }

    @GetMapping("/buscar")
    public List<Producto> buscarProductos(@RequestParam String filtro) {
        return productoService.busquedaDinamica(filtro);
    }

    @GetMapping("/{codProducto}")
    public ResponseEntity<Producto> getProductoByCodProducto(@PathVariable Long codProducto, @RequestParam Long establecimientoId) {
        try {
            Producto producto = productoService.buscarPorCodProducto(codProducto, establecimientoId);
            if (producto != null) {
                return ResponseEntity.ok(producto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(value = "createOrUpdate")
    public ResponseEntity<?> createProducto(@RequestBody Producto producto, @RequestParam Long establecimientoId) {
        try {
            Producto nuevoProducto = productoService.saveOrUpdateProduct(producto);
            return ResponseEntity.ok(nuevoProducto);
        }  catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

 /*    @PutMapping(value = "update/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto producto, @RequestParam Long establecimientoId) {
        return productoService.findById(id, establecimientoId)
                .map(produtoExistente -> {
                    producto.setCodProducto(id);
                    return ResponseEntity.ok(productoService.save(producto, establecimientoId));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    } */

    /* @PostMapping("/cargaMasiva")
    public ResponseEntity<Map<String, Integer>> cargaMasiva(@RequestBody List<Producto> productos, @RequestParam Long establecimientoId) {
        int created = 0;
        int updated = 0;
        int errors = 0;

        for (Producto producto : productos) {
            try {
                if (productoService.existeProducto(producto.getCodBarr(), establecimientoId)) {
                    productoService.actualizarProducto(producto, establecimientoId);
                    updated++;
                } else {
                    productoService.save(producto, establecimientoId);
                    created++;
                }
            } catch (Exception e) {
                errors++;
            }
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("created", created);
        result.put("updated", updated);
        result.put("errors", errors);

        return ResponseEntity.ok(result);
    } */
    
    /* @GetMapping("/getByCodBarr/{codBarr}")
    public ResponseEntity<Producto> getByCodBarr(@PathVariable String codBarr, @RequestParam Long establecimientoId) {
        Producto producto = productoService.findByCodBarr(codBarr, establecimientoId);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    } */


}
