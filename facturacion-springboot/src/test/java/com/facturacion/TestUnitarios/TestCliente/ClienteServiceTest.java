package com.facturacion.TestUnitarios.TestCliente;

import com.facturacion.entity.Cliente;
import com.facturacion.repository.ClienteRepository;
import com.facturacion.service.ClienteService;
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
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setRucDni("123456789");
        cliente.setNombre("Nombre Cliente");
        cliente.setDireccion("Direccion Cliente");
        cliente.setCorreo("correo@example.com");
    }

    @Test
    public void testListarClientes() {
        List<Cliente> clientes = Arrays.asList(cliente, cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.listarClientes();
        assertEquals(2, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerClientePorId() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        Optional<Cliente> result = clienteService.obtenerClientePorId(1);
        assertTrue(result.isPresent());
        assertEquals(cliente.getIdCliente(), result.get().getIdCliente());
        verify(clienteRepository, times(1)).findById(1);
    }

    @Test
    public void testCrearCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.crearCliente(cliente);
        assertEquals(cliente.getIdCliente(), result.getIdCliente());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testActualizarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        clienteService.actualizarCliente(cliente);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testEliminarCliente() {
        doNothing().when(clienteRepository).deleteById(anyInt());

        clienteService.eliminarCliente(1);
        verify(clienteRepository, times(1)).deleteById(1);
    }

    @Test
    public void testVerificarSiExiteCliente() {
        when(clienteRepository.verificarSiExiteCliente(anyString())).thenReturn("1");

        String result = clienteService.verificarSiExiteCliente("123456789");
        assertEquals("1", result);
        verify(clienteRepository, times(1)).verificarSiExiteCliente("123456789");
    }
}
