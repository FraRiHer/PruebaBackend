package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.DetallePedido;
import com.example.demo.entities.Establecimiento;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Producto;
import com.example.demo.entities.Proveedor;
import com.example.demo.repository.DetallePedidoRepository;
import com.example.demo.repository.EstablecimientoRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.ProveedorRepository;
import com.example.demo.requestBody.PedidoRequest;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional
    public Pedido createOrUpdatePedido(Pedido pedido, Long establecimientoId) {
        Establecimiento establecimiento = establecimientoRepository.findById(establecimientoId)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado"));
        pedido.setEstablecimiento(establecimiento);
        Proveedor proveedor = proveedorRepository.findById(pedido.getProveedor().getCodProv())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        pedido.setStatus("EN ESPERA");
        pedido.setProveedor(proveedor);
        pedido.setFechaRegistro(LocalDate.now());
        pedido.setTotal(BigDecimal.ZERO);

        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto producto = productoRepository.findByCodBarr(detalle.getProducto().getCodBarr())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            detalle.setProducto(producto);

            // Manejar valores null que vienen del frontend
            if (detalle.getPrecioCompra() == null) {
                detalle.setPrecioCompra(BigDecimal.ZERO);
            }
            if (detalle.getTotal() == null) {
                detalle.setTotal(BigDecimal.ZERO);
            }
            detallePedidoRepository.save(detalle);
        }
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido finalizarPedido(Long pedidoId, List<PedidoRequest> productosRecibidos) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!pedido.getStatus().equals("EN ESPERA")
                || pedidoRepository.findByNumeroFactura(productosRecibidos.get(0).getNumFactura()).isPresent()) {
            throw new IllegalStateException("El pedido ya ha sido finalizado o no está en espera.");
        }

        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedido detalle : pedido.getDetalles()) {
            Optional<PedidoRequest> recibidoOpt = productosRecibidos.stream()
                    .filter(d -> d.getInventario().getProducto().getCodBarr()
                            .equals(detalle.getProducto().getCodBarr()))
                    .findFirst();

            if (recibidoOpt.isPresent()) {
                PedidoRequest recibido = recibidoOpt.get();
                inventarioService.updateInventoryOnReception(recibido, pedido.getEstablecimiento().getId());

                detalle.setCantidad(recibido.getInventario().getCantidad());
                detalle.setPrecioCompra(recibido.getPrecioCompra());

                // Asegúrate de calcular el total basado en cantidad * precioCompra
                BigDecimal subtotal = recibido.getPrecioCompra()
                        .multiply(BigDecimal.valueOf(recibido.getInventario().getCantidad()));
                detalle.setTotal(subtotal);

                // Sumar el subtotal al total del pedido
                total = total.add(subtotal);

                detallePedidoRepository.save(detalle);
            } else {
                System.out.println("Producto no encontrado en la recepción: " + detalle.getProducto().getNombre());
            }
        }

        pedido.setNumeroFactura(productosRecibidos.get(0).getNumFactura());
        pedido.setTotal(total); // Establecer el total del pedido
        pedido.setStatus("FINALIZADO");
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public List<Pedido> findPedidosByProveedorAndEstablecimiento(Long codProv, Long establecimientoId) {
        return pedidoRepository.findPedidosByProveedorAndEstablecimiento(codProv, establecimientoId);
    }

    @Transactional
    public List<Pedido> findPedidosEnEsperaPorEstablecimiento(Long establecimientoId) {
        return pedidoRepository.findByEstablecimientoIdAndStatus(establecimientoId, "EN ESPERA");
    }

    @Transactional
    public List<Pedido> findByEstablecimiento(Long establecimientoId) {
        return pedidoRepository.findByEstablecimientoId(establecimientoId);
    }
  
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    

}
