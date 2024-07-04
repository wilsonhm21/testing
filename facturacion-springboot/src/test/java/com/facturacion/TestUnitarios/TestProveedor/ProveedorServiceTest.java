package com.facturacion.TestUnitarios.TestProveedor;

import com.facturacion.entity.Proveedor;
import com.facturacion.repository.ProveedorRepository;
import com.facturacion.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    private Proveedor proveedor;

    @BeforeEach
    public void setUp() {
        proveedor = new Proveedor();
        proveedor.setIdProveedor(1);
        proveedor.setRuc("123456789");
        proveedor.setNombre("Nombre Proveedor");
        proveedor.setDireccion("Direccion Proveedor");
        proveedor.setCorreo("correo@example.com");
    }

    @Test
    public void testListarProveedor() {
        List<Proveedor> proveedores = Arrays.asList(proveedor, proveedor);
        when(proveedorRepository.findAll()).thenReturn(proveedores);

        List<Proveedor> result = proveedorService.listarProveedor();
        assertEquals(2, result.size());
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerProveedorPorId() {
        when(proveedorRepository.findById(anyInt())).thenReturn(Optional.of(proveedor));

        Optional<Proveedor> result = proveedorService.obtenerProveedorPorId(1);
        assertTrue(result.isPresent());
        assertEquals(proveedor.getIdProveedor(), result.get().getIdProveedor());
        verify(proveedorRepository, times(1)).findById(1);
    }

    @Test
    public void testCrearProveedor() {
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);

        Proveedor result = proveedorService.crearProveedor(proveedor);
        assertEquals(proveedor.getIdProveedor(), result.getIdProveedor());
        verify(proveedorRepository, times(1)).save(proveedor);
    }

    @Test
    public void testActualizarProveedor() {
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);

        proveedorService.actualizarProveedor(proveedor);
        verify(proveedorRepository, times(1)).save(proveedor);
    }

    @Test
    public void testEliminarProveedor() {
        doNothing().when(proveedorRepository).deleteById(anyInt());

        proveedorService.eliminarProveedor(1);
        verify(proveedorRepository, times(1)).deleteById(1);
    }

    @Test
    public void testVerificarSiExiteProveedor() {
        when(proveedorRepository.verificarSiExiteProveedor(anyString())).thenReturn("1");

        String result = proveedorService.verificarSiExiteProveedor("123456789");
        assertEquals("1", result);
        verify(proveedorRepository, times(1)).verificarSiExiteProveedor("123456789");
    }
}
