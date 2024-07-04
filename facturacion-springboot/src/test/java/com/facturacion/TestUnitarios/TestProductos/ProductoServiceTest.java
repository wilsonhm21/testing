package com.facturacion.TestUnitarios.TestProductos;

import com.facturacion.dto.DetFacturaDTO;
import com.facturacion.entity.Producto;
import com.facturacion.repository.ProductoRepository;
import com.facturacion.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarProducto() {
        List<Producto> productos = Arrays.asList(new Producto(), new Producto());
        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> result = productoService.listarProducto();
        assert(result.size() == 2);
    }

    @Test
    public void testObtenerProductoPorId() {
        Producto producto = new Producto();
        when(productoRepository.findById(anyInt())).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.obtenerProductoPorId(1);
        assert(result.isPresent());
    }

    @Test
    public void testCrearProducto() {
        Producto producto = new Producto();
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto result = productoService.crearProducto(producto);
        assert(result != null);
    }

    @Test
    public void testActualizarProducto() {
        Producto producto = new Producto();
        productoService.actualizarProducto(producto);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    public void testEliminarProducto() {
        productoService.eliminarProducto(1);
        verify(productoRepository, times(1)).deleteById(1);
    }

    @Test
    public void testVerificarSiExiteElCodProducto() {
        when(productoRepository.verificarSiExiteElCodProducto(anyString())).thenReturn("codigo");

        String result = productoService.verificarSiExiteElCodProducto("codigo");
        assert(result.equals("codigo"));
    }

    @Test
    public void testDisminuirStock() {
        DetFacturaDTO detFacturaDTO = new DetFacturaDTO();
        detFacturaDTO.setCodigoProducto(1);
        detFacturaDTO.setCantidad(5);

        when(productoRepository.disminuirStock(anyInt(), anyInt())).thenReturn(1);

        productoService.disminuirStock(Arrays.asList(detFacturaDTO));
        verify(productoRepository, times(1)).disminuirStock(1, 5);
    }
}
