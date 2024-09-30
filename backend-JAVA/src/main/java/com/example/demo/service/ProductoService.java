package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Producto;
import com.example.demo.entities.Fabricante;
import com.example.demo.entities.Grupo;
import com.example.demo.entities.Inventario;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.FabricanteRepository;
import com.example.demo.repository.GrupoRepository;
import com.example.demo.repository.InventarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FabricanteRepository fabricanteRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto buscarPorCodProducto(Long codProducto, Long establecimientoId) {
        return inventarioRepository.findByProductoCodProductoAndEstablecimientoId(codProducto, establecimientoId)
                .map(Inventario::getProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public boolean existeProducto(Producto producto) {
        Optional<Producto> existeProduct = productoRepository.findByCodBarr(producto.getCodBarr());
        return existeProduct.isPresent();
    }

    public List<Producto> busquedaDinamica(String filtro) {
        return productoRepository.searchByNombreOrCodBarr(filtro);
    }

    public List<Producto> findAll(Long establecimientoId) {
        return inventarioRepository.findByEstablecimientoId(establecimientoId).stream()
                .map(Inventario::getProducto)
                .toList();
    }

    public Optional<Producto> findById(Long id, Long establecimientoId) {
        return inventarioRepository.findByProductoCodProductoAndEstablecimientoId(id, establecimientoId)
                .map(Inventario::getProducto);
    }

    @Transactional
    public Producto saveOrUpdateProduct(Producto productoDTO) {
        Optional<Producto> existingProduct = productoRepository.findByCodBarr(productoDTO.getCodBarr());

        if (existingProduct.isPresent()) {
            Producto product = existingProduct.get();
            product.setNombre(productoDTO.getNombre());
            product.setIva(productoDTO.getIva());
            product.setFabricante(productoDTO.getFabricante());
            product.setPresentacion(productoDTO.getPresentacion());
            product.setGrupo(productoDTO.getGrupo());
            return productoRepository.save(product);
        } else {
            Fabricante fabricante = fabricanteRepository.findById(productoDTO.getFabricante().getId())
                    .orElseGet(() -> fabricanteRepository.save(productoDTO.getFabricante()));
            Grupo grupo = grupoRepository.findById(productoDTO.getGrupo().getCodGrupo())
                    .orElseGet(() -> grupoRepository.save(productoDTO.getGrupo()));

            Producto producto = new Producto();
            producto.setCodBarr(productoDTO.getCodBarr());
            producto.setNombre(productoDTO.getNombre());
            producto.setIva(productoDTO.getIva());
            producto.setGrupo(grupo);
            producto.setFabricante(fabricante);
            producto.setPresentacion(productoDTO.getPresentacion());

            return productoRepository.save(producto);
        }
    }
}
