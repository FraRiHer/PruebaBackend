package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

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
import com.example.demo.requestBody.FacturaRequest;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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
    public Factura crearFactura(FacturaRequest facturaRequest) {
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

            // Actualizar el inventario
            Inventario inventario = inventarioRepository.findByProductoCodBarrAndEstablecimientoId(producto.getCodBarr(), establecimiento.getId())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
            inventario.setCantidad(inventario.getCantidad() - detalleRequest.getCantidad());
            inventarioRepository.save(inventario);
        }
        return factura;
    }

    public byte[] generarPdfFactura(Factura factura) throws DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();

        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font fontSubTitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

        Paragraph titulo = new Paragraph("Factura", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(new Paragraph("Nombre de la Droguería: " + factura.getEstablecimiento().getDrogueria().getNombre(), fontNormal));
        document.add(new Paragraph("Dirección del Establecimiento: " + factura.getEstablecimiento().getDireccion(), fontNormal));
        document.add(new Paragraph("Número de Factura: " + factura.getNum_fact(), fontNormal));
        document.add(new Paragraph("Fecha: " + factura.getFecha(), fontNormal));
        document.add(new Paragraph("Cliente: " + factura.getCliente().getNombre(), fontNormal));
        document.add(new Paragraph("Empleado: " + factura.getEmpleado().getNombre(), fontNormal));
        document.add(new Paragraph("Método de Pago: " + factura.getMetodoPago().getDescripcion(), fontNormal));
        document.add(new Paragraph(" ", fontNormal)); // Espacio

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 2, 2, 2, 2});

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Producto", fontSubTitulo));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Cantidad", fontSubTitulo));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Precio Unitario", fontSubTitulo));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Descuento", fontSubTitulo));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Subtotal", fontSubTitulo));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        if (factura.getDetalles() != null) {
            for (DetalleFactura detalle : factura.getDetalles()) {
                BigDecimal precioUnitario = detalle.getPrecioUnitario();
                BigDecimal descuento = detalle.getDescuento();
                BigDecimal cantidad = BigDecimal.valueOf(detalle.getCantidad());
                BigDecimal subtotal = cantidad.multiply(precioUnitario.subtract(descuento));

                table.addCell(new Phrase(detalle.getProducto().getNombre(), fontNormal));
                table.addCell(new Phrase(String.valueOf(detalle.getCantidad()), fontNormal));
                table.addCell(new Phrase(precioUnitario.toString(), fontNormal));
                table.addCell(new Phrase(descuento.toString(), fontNormal));
                table.addCell(new Phrase(subtotal.toString(), fontNormal));
            }
        }

        document.add(table);

        Paragraph total = new Paragraph("Total: " + factura.getTotalVenta(), fontSubTitulo);
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
