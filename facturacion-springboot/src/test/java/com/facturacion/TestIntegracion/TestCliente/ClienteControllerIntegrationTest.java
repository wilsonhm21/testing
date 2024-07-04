package com.facturacion.TestIntegracion.TestCliente;

import com.facturacion.entity.Cliente;
import com.facturacion.repository.ClienteRepository;
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
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        clienteRepository.deleteAll();
    }

    @Test
    public void testCrearCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setRucDni("123456789012345");
        cliente.setNombre("Cliente Test");
        cliente.setDireccion("Direccion Test");
        cliente.setCorreo("cliente@test.com");
        cliente.setFechaCreacion(LocalDate.now());

        mockMvc.perform(post("/clientes/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rucDni", is("123456789012345")))
                .andExpect(jsonPath("$.nombre", is("Cliente Test")));
    }

    @Test
    public void testObtenerClientePorId() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setRucDni("987654321012345");
        cliente.setNombre("Cliente Test 2");
        cliente.setDireccion("Direccion Test 2");
        cliente.setCorreo("cliente2@test.com");
        cliente.setFechaCreacion(LocalDate.now());
        cliente = clienteRepository.save(cliente);

        mockMvc.perform(get("/clientes/{id}", cliente.getIdCliente()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rucDni", is("987654321012345")))
                .andExpect(jsonPath("$.nombre", is("Cliente Test 2")));
    }

    @Test
    public void testListarClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setRucDni("112233445566789");
        cliente.setNombre("Cliente Test 3");
        cliente.setDireccion("Direccion Test 3");
        cliente.setCorreo("cliente3@test.com");
        cliente.setFechaCreacion(LocalDate.now());
        clienteRepository.save(cliente);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rucDni", is("112233445566789")))
                .andExpect(jsonPath("$[0].nombre", is("Cliente Test 3")));
    }

    @Test
    public void testEliminarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setRucDni("223344556678901");
        cliente.setNombre("Cliente Test 4");
        cliente.setDireccion("Direccion Test 4");
        cliente.setCorreo("cliente4@test.com");
        cliente.setFechaCreacion(LocalDate.now());
        cliente = clienteRepository.save(cliente);

        mockMvc.perform(delete("/clientes/eliminar/{id}", cliente.getIdCliente()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/clientes/{id}", cliente.getIdCliente()))
                .andExpect(status().isNotFound());
    }
}
