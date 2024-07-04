package com.facturacion.TestIntegracion.TestCliente;

import com.facturacion.entity.Cliente;
import com.facturacion.repository.ClienteRepository;
import com.facturacion.service.ClienteService;
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
public class ClienteServiceIntegrationTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setup() {
        clienteRepository.deleteAll();
    }

    @Test
    public void testCrearCliente() {
        Cliente cliente = new Cliente();
        cliente.setRucDni("123456789012345"); // Asegúrate de que el valor tenga como máximo 15 caracteres
        cliente.setNombre("Cliente Test");
        cliente.setDireccion("Direccion Test");
        cliente.setCorreo("cliente@test.com");

        Cliente savedCliente = clienteService.crearCliente(cliente);

        assertThat(savedCliente).isNotNull();
        assertThat(savedCliente.getIdCliente()).isNotNull();
        assertThat(savedCliente.getRucDni()).isEqualTo("123456789012345");
        assertThat(savedCliente.getNombre()).isEqualTo("Cliente Test");
    }

    @Test
    public void testObtenerClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setRucDni("987654321012345"); // Asegúrate de que el valor tenga como máximo 15 caracteres
        cliente.setNombre("Cliente Test 2");
        cliente.setDireccion("Direccion Test 2");
        cliente.setCorreo("cliente2@test.com");
        clienteRepository.save(cliente);

        Optional<Cliente> foundCliente = clienteService.obtenerClientePorId(cliente.getIdCliente());

        assertThat(foundCliente).isPresent();
        assertThat(foundCliente.get().getRucDni()).isEqualTo("987654321012345");
        assertThat(foundCliente.get().getNombre()).isEqualTo("Cliente Test 2");
    }

    @Test
    public void testListarClientes() {
        Cliente cliente = new Cliente();
        cliente.setRucDni("112233445566789"); // Asegúrate de que el valor tenga como máximo 15 caracteres
        cliente.setNombre("Cliente Test 3");
        cliente.setDireccion("Direccion Test 3");
        cliente.setCorreo("cliente3@test.com");
        clienteRepository.save(cliente);

        List<Cliente> clientes = clienteService.listarClientes();

        assertThat(clientes).isNotEmpty();
        assertThat(clientes.get(0).getRucDni()).isEqualTo("112233445566789");
        assertThat(clientes.get(0).getNombre()).isEqualTo("Cliente Test 3");
    }

    @Test
    public void testEliminarCliente() {
        Cliente cliente = new Cliente();
        cliente.setRucDni("223344556678901"); // Asegúrate de que el valor tenga como máximo 15 caracteres
        cliente.setNombre("Cliente Test 4");
        cliente.setDireccion("Direccion Test 4");
        cliente.setCorreo("cliente4@test.com");
        clienteRepository.save(cliente);

        clienteService.eliminarCliente(cliente.getIdCliente());

        Optional<Cliente> deletedCliente = clienteRepository.findById(cliente.getIdCliente());
        assertThat(deletedCliente).isNotPresent();
    }
}
