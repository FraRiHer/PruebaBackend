package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Establecimiento;
import com.example.demo.entities.Inventario;
import com.example.demo.entities.Producto;
import com.example.demo.repository.EstablecimientoRepository;
import com.example.demo.repository.InventarioRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.requestBody.PedidoRequest;

@Service
public class InventarioService {
    
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired 
    private EstablecimientoRepository establecimientoRepository;
    @Transactional
    public void createOrUpdateInventory(Inventario inventarioDTO, Long establecimientoId) {
        // Verifica si el establecimiento existe
        Establecimiento establecimiento = establecimientoRepository.findById(establecimientoId)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado"));

        // Verifica si el producto existe
        Producto producto = productoRepository.findByCodBarr(inventarioDTO.getProducto().getCodBarr())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verifica si el inventario ya existe por el código de barras y establecimiento
        Optional<Inventario> inventarioExistente = inventarioRepository.findByProductoCodBarrAndEstablecimientoId(
                producto.getCodBarr(), establecimientoId);

        if (inventarioExistente.isPresent()) {
            // Actualiza el inventario existente
            Inventario inventario = inventarioExistente.get();
            inventario.setPrecioVenta(inventarioDTO.getPrecioVenta());
            inventario.setUbicacion(inventarioDTO.getUbicacion());
            inventario.setCantidad(inventarioDTO.getCantidad());
            inventario.getProducto().setGrupo(inventarioDTO.getProducto().getGrupo());
            inventario.setPrecioVentaBlis(inventarioDTO.getPrecioVentaBlis());
            inventario.setPrecioVentaUnid(inventarioDTO.getPrecioVentaUnid());
            inventarioRepository.save(inventario); // Guardar cambios
        } else {
            // Asigna el establecimiento y el producto existente al nuevo inventario
            inventarioDTO.setEstablecimiento(establecimiento);
            inventarioDTO.setProducto(producto); // Asigna el producto que ya existe
            inventarioRepository.save(inventarioDTO);
        }
    }

    @Transactional
    public void updateInventoryOnReception(PedidoRequest inventarioRecibido, Long establecimientoId) {
        Producto producto = productoRepository
                .findByCodBarr(inventarioRecibido.getInventario().getProducto().getCodBarr())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Inventario inventarioExistente = inventarioRepository
                .findByProductoCodBarrAndEstablecimientoId(
                        inventarioRecibido.getInventario().getProducto().getCodBarr(), establecimientoId)
                .orElse(new Inventario()); // Si no existe inventario, se crea uno nuevo

        // Si el inventario es nuevo, asignar el producto
        if (inventarioExistente.getProducto() == null) {
            inventarioExistente.setProducto(producto);
        }

        int nuevaCantidad = inventarioExistente.getCantidad() + inventarioRecibido.getInventario().getCantidad();
        inventarioExistente.setCantidad(nuevaCantidad);

        if (inventarioRecibido.getInventario().getPrecioVenta() != null) {
            inventarioExistente.setPrecioVenta(inventarioRecibido.getInventario().getPrecioVenta());
        }

        if (inventarioRecibido.getInventario().getProducto().getPresentacion() != null) {
            inventarioExistente.getProducto()
                    .setPresentacion(inventarioRecibido.getInventario().getProducto().getPresentacion());
        }

        // Asignar ubicación si es null
        if (inventarioRecibido.getInventario().getUbicacion() != null) {
            inventarioExistente.setUbicacion(inventarioRecibido.getInventario().getUbicacion());
        } else {
            inventarioExistente.setUbicacion("Ubicación desconocida"); // Valor predeterminado
        }

        inventarioRepository.save(inventarioExistente);
    }

    public Optional<Inventario> consultarInventario(String codBarr, Long establecimientoId) {
        return inventarioRepository.findByProductoCodBarrAndEstablecimientoId(codBarr, establecimientoId);
    }

    @Transactional
    public Optional<Inventario> consultarInventarioPorNombre(String nombre, Long establecimientoId) {
        return inventarioRepository.findByProductoNombreAndEstablecimientoId(nombre, establecimientoId);
    }

    public Inventario findInventoryByCodBarrAndEstablecimiento(String codBarr, Long establecimientoId) {
        return inventarioRepository.findByProductoCodBarrAndEstablecimientoId(codBarr, establecimientoId).orElse(null);
    }

    public List<Inventario> findAllInventoryByEstablecimiento(Long establecimientoId) {
        return inventarioRepository.findByEstablecimientoId(establecimientoId);
    }

    public List<Inventario> busquedaDinamica(String filtro, Long establecimientoId) {
        /*
         * List<Inventario> lista = inventarioRepository.
         * findByProductoNombreContainingIgnoreCaseAndEstablecimientoId(filtro,
         * establecimientoId);
         * System.out.println(lista.get(0));
         */
        return inventarioRepository.findByProductoNombreContainingIgnoreCaseAndEstablecimientoId(filtro,
                establecimientoId);
    }
}
