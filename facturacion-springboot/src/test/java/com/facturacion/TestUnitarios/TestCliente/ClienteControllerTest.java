package com.facturacion.TestUnitarios.TestCliente;

import com.facturacion.controller.ClienteController;
import com.facturacion.entity.Cliente;
import com.facturacion.service.ClienteService;
import com.facturacion.util.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @MockBean
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    public void testListarClientes() throws Exception {
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteService.listarClientes()).thenReturn(clientes);

        mockMvc.perform(get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testObtenerClientePorId() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1); // Asegúrate de que el campo idCliente esté configurado
        when(clienteService.obtenerClientePorId(anyInt())).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCliente").exists());
    }

    @Test
    public void testObtenerClientePorIdNotFound() throws Exception {
        when(clienteService.obtenerClientePorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testVerificarSiExiteCliente() throws Exception {
        when(clienteService.verificarSiExiteCliente(anyString())).thenReturn("1");

        mockMvc.perform(get("/clientes/verificar-cliente/{ruc_dni}", "123456789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value("1"));
    }

    @Test
    public void testCrearCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);
        when(clienteService.crearCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rucDni\": \"123456789\", \"nombre\": \"nombre\", \"direccion\": \"direccion\", \"correo\": \"correo@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCliente").value(1));
    }

    @Test
    public void testActualizarCliente() throws Exception {
        doNothing().when(clienteService).actualizarCliente(any(Cliente.class));

        mockMvc.perform(put("/clientes/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idCliente\": 1, \"rucDni\": \"123456789\", \"nombre\": \"nombre\", \"direccion\": \"direccion\", \"correo\": \"correo@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEliminarCliente() throws Exception {
        when(clienteService.obtenerClientePorId(anyInt())).thenReturn(Optional.of(new Cliente()));
        doNothing().when(clienteService).eliminarCliente(anyInt());

        mockMvc.perform(delete("/clientes/eliminar/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testEliminarClienteNotFound() throws Exception {
        when(clienteService.obtenerClientePorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/clientes/eliminar/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
