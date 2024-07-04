package com.facturacion.TestIntegracion.TestUsuarios;

import com.facturacion.entity.Usuario;
import com.facturacion.repository.UsuarioRepository;
import com.facturacion.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsuarioServiceIntegrationTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setup() {
        usuarioRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("password123");
        usuario.setIntentosFallidos(0);
        usuario.setBloqueado((byte) 0);
        usuarioRepository.save(usuario);
    }

    @Test
    public void testBuscarPorUsuarioYContrasenia() {
        Optional<Object[]> usuario = usuarioService.buscarPorUsuarioYContrasenia("testuser", "password123");
        assertThat(usuario).isPresent();
    }

    @Test
    public void testObtenerIntentosFallidos() {
        Optional<Integer> intentos = usuarioService.obtenerIntentosFallidos("testuser");
        assertThat(intentos).isPresent();
        assertThat(intentos.get()).isEqualTo(0);
    }

    @Test
    public void testEstaBloqueado() {
        boolean bloqueado = usuarioService.estaBloqueado("testuser");
        assertThat(bloqueado).isFalse();
    }

    @Test
    @Transactional
    public void testIncrementarIntentosFallidos() {
        usuarioService.incrementarIntentosFallidos("testuser");
        Optional<Integer> intentos = usuarioService.obtenerIntentosFallidos("testuser");
        assertThat(intentos).isPresent();
        assertThat(intentos.get()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void testBloquearUsuario() {
        usuarioService.bloquearUsuario("testuser");
        boolean bloqueado = usuarioService.estaBloqueado("testuser");
        assertThat(bloqueado).isTrue();
    }
}
