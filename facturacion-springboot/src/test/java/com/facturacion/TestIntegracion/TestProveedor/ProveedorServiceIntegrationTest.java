package com.facturacion.TestIntegracion.TestProveedor;

import com.facturacion.entity.Proveedor;
import com.facturacion.repository.ProveedorRepository;
import com.facturacion.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProveedorServiceIntegrationTest {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @BeforeEach
    public void setup() {
        proveedorRepository.deleteAll();
    }

    @Test
    public void testCrearProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("123456789012345");
        proveedor.setNombre("Proveedor Test");
        proveedor.setDireccion("Direccion Test");
        proveedor.setCorreo("proveedor@test.com");
        proveedor.setFechaCreacion(LocalDate.now());

        Proveedor nuevoProveedor = proveedorService.crearProveedor(proveedor);

        assertThat(nuevoProveedor).isNotNull();
        assertThat(nuevoProveedor.getIdProveedor()).isNotNull();
        assertThat(nuevoProveedor.getRuc()).isEqualTo("123456789012345");
    }

    @Test
    public void testObtenerProveedorPorId() {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("987654321012345");
        proveedor.setNombre("Proveedor Test 2");
        proveedor.setDireccion("Direccion Test 2");
        proveedor.setCorreo("proveedor2@test.com");
        proveedor.setFechaCreacion(LocalDate.now());
        proveedor = proveedorRepository.save(proveedor);

        Optional<Proveedor> encontrado = proveedorService.obtenerProveedorPorId(proveedor.getIdProveedor());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getRuc()).isEqualTo("987654321012345");
    }

    @Test
    public void testListarProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("112233445566789");
        proveedor.setNombre("Proveedor Test 3");
        proveedor.setDireccion("Direccion Test 3");
        proveedor.setCorreo("proveedor3@test.com");
        proveedor.setFechaCreacion(LocalDate.now());
        proveedorRepository.save(proveedor);

        Iterable<Proveedor> proveedores = proveedorService.listarProveedor();

        assertThat(proveedores).hasSize(1);
        assertThat(proveedores.iterator().next().getRuc()).isEqualTo("112233445566789");
    }

    @Test
    public void testEliminarProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("223344556678901");
        proveedor.setNombre("Proveedor Test 4");
        proveedor.setDireccion("Direccion Test 4");
        proveedor.setCorreo("proveedor4@test.com");
        proveedor.setFechaCreacion(LocalDate.now());
        proveedor = proveedorRepository.save(proveedor);

        proveedorService.eliminarProveedor(proveedor.getIdProveedor());

        Optional<Proveedor> encontrado = proveedorService.obtenerProveedorPorId(proveedor.getIdProveedor());
        assertThat(encontrado).isNotPresent();
    }
}
