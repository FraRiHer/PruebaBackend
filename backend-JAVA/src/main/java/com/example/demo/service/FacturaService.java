package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.DetalleFactura;
import com.example.demo.entities.Empleado;
import com.example.demo.entities.Establecimiento;
import com.example.demo.entities.Factura;
import com.example.demo.entities.Inventario;
import com.example.demo.entities.MetodoPago;
import com.example.demo.entities.Producto;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.DetalleFacturaRepository;
import com.example.demo.repository.EmpleadoRepository;
import com.example.demo.repository.EstablecimientoRepository;
import com.example.demo.repository.FacturaRepository;
import com.example.demo.repository.InventarioRepository;
import com.example.demo.repository.MetodoPagoRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.requestBody.DetalleFacturaRequest;
import com.example.demo.requestBody.DetalleFacturaResponse;
import com.example.demo.requestBody.FacturaRequest;
import com.example.demo.requestBody.FacturaResponse;

import jakarta.transaction.Transactional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Transactional
    public FacturaResponse crearFactura(FacturaRequest facturaRequest) {
        Cliente cliente = clienteRepository.findById(facturaRequest.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Empleado empleado = empleadoRepository.findById(facturaRequest.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        MetodoPago metodoPago = metodoPagoRepository.findById(facturaRequest.getMetodoPagoId())
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        Establecimiento establecimiento = establecimientoRepository.findById(facturaRequest.getEstablecimientoId())
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado"));
 
        Factura factura = new Factura();
        factura.setFecha(LocalDate.now());
        factura.setCliente(cliente);
        factura.setEmpleado(empleado);
        factura.setMetodoPago(metodoPago);
        factura.setTotalVenta(facturaRequest.getTotalVenta());
        factura.setEstablecimiento(establecimiento);
        factura.setDetalles(new ArrayList<>());
        factura = facturaRepository.save(factura);
        System.out.println(factura.getNum_fact());

        List<DetalleFacturaResponse> detalleResponses = new ArrayList<>();

        for (DetalleFacturaRequest detalleRequest : facturaRequest.getDetalles()) {
            Producto producto = productoRepository.findById(detalleRequest.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            DetalleFactura detalle = new DetalleFactura();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setDescuento(detalleRequest.getDescuento());
            detalle.setPrecioUnitario(detalleRequest.getPrecioUnitario());
            detalle.setFactura(factura);
            factura.getDetalles().add(detalle);
            detalleFacturaRepository.save(detalle);

            // Construir DetalleFacturaResponse para cada detalle
            DetalleFacturaResponse detalleResponse = new DetalleFacturaResponse();
            detalleResponse.setNombreProducto(producto.getNombre());
            detalleResponse.setCantidad(detalle.getCantidad());
            detalleResponse.setPrecioUnitario(detalle.getPrecioUnitario());
            detalleResponse.setDescuento(detalle.getDescuento());
            detalleResponse.setSubtotal(detalle.getPrecioUnitario().subtract(detalle.getDescuento())
                    .multiply(new BigDecimal(detalle.getCantidad())));
            detalleResponses.add(detalleResponse);

            // Actualizar el inventario
            Inventario inventario = inventarioRepository
                    .findByProductoCodBarrAndEstablecimientoId(producto.getCodBarr(), establecimiento.getId())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
            inventario.setCantidad(inventario.getCantidad() - detalleRequest.getCantidad());
            inventarioRepository.save(inventario);
        }

        // Crear y retornar FacturaResponse con todos los datos necesarios
        FacturaResponse facturaResponse = new FacturaResponse();
        facturaResponse.setNumFactura(factura.getNum_fact());
        facturaResponse.setNombreCliente(cliente.getNombre());
        facturaResponse.setNombreEmpleado(empleado.getNombre());
        facturaResponse.setMetodoPago(metodoPago.getDescripcion());
        facturaResponse.setFecha(factura.getFecha());
        facturaResponse.setTotalVenta(factura.getTotalVenta());
        facturaResponse.setNombreDrogueria(establecimiento.getDrogueria().getNombre());
        facturaResponse.setDireccionEstablecimiento(establecimiento.getDireccion());
        facturaResponse.setDetalles(detalleResponses);
        System.out.println("Num factResponse:" + facturaResponse.getNumFactura());
        System.out.println("Detalles de la factura generados: " + detalleResponses.size());
        return facturaResponse;
    }

    public List<Factura> getByEstablecimiento(Long establecimientoId){
        return facturaRepository.findByEstablecimientoId(establecimientoId);
    }

}
