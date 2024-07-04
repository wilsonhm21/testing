package com.facturacion.TestIntegracion.TestProducto;

import com.facturacion.entity.Producto;
import com.facturacion.repository.ProductoRepository;
import com.facturacion.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductoServiceIntegrationTest {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    public void setup() {
        productoRepository.deleteAll();
    }

    @Test
    public void testCrearProducto() {
        Producto producto = new Producto();
        producto.setCodigo("P001_UNIQUE"); // Usar un código único
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);
        producto.setStock(50.0);

        Producto savedProducto = productoService.crearProducto(producto);

        assertThat(savedProducto).isNotNull();
        assertThat(savedProducto.getIdProducto()).isNotNull();
        assertThat(savedProducto.getCodigo()).isEqualTo("P001_UNIQUE");
        assertThat(savedProducto.getNombre()).isEqualTo("Producto Test");
    }

    @Test
    public void testObtenerProductoPorId() {
        Producto producto = new Producto();
        producto.setCodigo("P002_UNIQUE");  // Usar un código único
        producto.setNombre("Producto Test 2");
        producto.setPrecio(200.0);
        producto.setStock(60.0);
        productoRepository.save(producto);

        Optional<Producto> foundProducto = productoService.obtenerProductoPorId(producto.getIdProducto());

        assertThat(foundProducto).isPresent();
        assertThat(foundProducto.get().getCodigo()).isEqualTo("P002_UNIQUE");
        assertThat(foundProducto.get().getNombre()).isEqualTo("Producto Test 2");
    }

    @Test
    public void testListarProducto() {
        Producto producto = new Producto();
        producto.setCodigo("P003_UNIQUE");  // Usar un código único
        producto.setNombre("Producto Test 3");
        producto.setPrecio(300.0);
        producto.setStock(70.0);
        productoRepository.save(producto);

        List<Producto> productos = productoService.listarProducto();

        assertThat(productos).isNotEmpty();
        assertThat(productos.get(0).getCodigo()).isEqualTo("P003_UNIQUE");
        assertThat(productos.get(0).getNombre()).isEqualTo("Producto Test 3");
    }

    @Test
    public void testEliminarProducto() {
        Producto producto = new Producto();
        producto.setCodigo("P004_UNIQUE");  // Usar un código único
        producto.setNombre("Producto Test 4");
        producto.setPrecio(400.0);
        producto.setStock(80.0);
        productoRepository.save(producto);

        productoService.eliminarProducto(producto.getIdProducto());

        Optional<Producto> deletedProducto = productoRepository.findById(producto.getIdProducto());
        assertThat(deletedProducto).isNotPresent();
    }
}
