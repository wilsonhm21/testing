package com.facturacion.TestIntegracion.TestProveedor;


import com.facturacion.entity.Proveedor;
import com.facturacion.repository.ProveedorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProveedorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        proveedorRepository.deleteAll();
    }

    @Test
    public void testCrearProveedor() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("123456789012345");
        proveedor.setNombre("Proveedor Test");
        proveedor.setDireccion("Direccion Test");
        proveedor.setCorreo("proveedor@test.com");
        proveedor.setFechaCreacion(LocalDate.now());

        mockMvc.perform(post("/proveedor/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ruc", is("123456789012345")))
                .andExpect(jsonPath("$.nombre", is("Proveedor Test")));
    }

    @Test
    public void testObtenerProveedorPorId() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("987654321012345");
        proveedor.setNombre("Proveedor Test 2");
        proveedor.setDireccion("Direccion Test 2");
        proveedor.setCorreo("proveedor2@test.com");
        proveedor.setFechaCreacion(LocalDate.now());
        proveedor = proveedorRepository.save(proveedor);

        mockMvc.perform(get("/proveedor/{id}", proveedor.getIdProveedor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ruc", is("987654321012345")))
                .andExpect(jsonPath("$.nombre", is("Proveedor Test 2")));
    }

    @Test
    public void testListarProveedor() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("112233445566789");
        proveedor.setNombre("Proveedor Test 3");
        proveedor.setDireccion("Direccion Test 3");
        proveedor.setCorreo("proveedor3@test.com");
        proveedor.setFechaCreacion(LocalDate.now());
        proveedorRepository.save(proveedor);

        mockMvc.perform(get("/proveedor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ruc", is("112233445566789")))
                .andExpect(jsonPath("$[0].nombre", is("Proveedor Test 3")));
    }

    @Test
    public void testEliminarProveedor() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("223344556678901");
        proveedor.setNombre("Proveedor Test 4");
        proveedor.setDireccion("Direccion Test 4");
        proveedor.setCorreo("proveedor4@test.com");
        proveedor.setFechaCreacion(LocalDate.now());
        proveedor = proveedorRepository.save(proveedor);

        mockMvc.perform(delete("/proveedor/eliminar/{id}", proveedor.getIdProveedor()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/proveedor/{id}", proveedor.getIdProveedor()))
                .andExpect(status().isNotFound());
    }
}
