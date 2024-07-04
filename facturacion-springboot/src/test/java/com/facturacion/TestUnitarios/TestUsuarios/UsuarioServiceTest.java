package com.facturacion.TestUnitarios.TestUsuarios;

import com.facturacion.entity.Usuario;
import com.facturacion.repository.UsuarioRepository;
import com.facturacion.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setUsername("admin");
        usuario.setPassword("admin");
        usuario.setIntentosFallidos(0);
        usuario.setBloqueado((byte) 0);
    }

    @Test
    public void testBuscarPorUsuarioYContrasenia() {
        when(usuarioRepository.findByUsernameAndPasswords(anyString(), anyString()))
                .thenReturn(Optional.of(new Object[] { usuario }));

        Optional<Object[]> result = usuarioService.buscarPorUsuarioYContrasenia("admin", "admin");
        assertTrue(result.isPresent());
        assertEquals("admin", ((Usuario) result.get()[0]).getUsername());
    }

    @Test
    public void testObtenerIntentosFallidos() {
        when(usuarioRepository.obtenerIntentosFallidos(anyString()))
                .thenReturn(Optional.of(1));

        Optional<Integer> result = usuarioService.obtenerIntentosFallidos("admin");
        assertTrue(result.isPresent());
        assertEquals(1, result.get());
    }

    @Test
    public void testEstaBloqueado() {
        when(usuarioRepository.estaBloqueado(anyString()))
                .thenReturn(Optional.of((byte) 1));

        boolean result = usuarioService.estaBloqueado("admin");
        assertTrue(result);
    }

    @Test
    public void testIncrementarIntentosFallidos() {
        doNothing().when(usuarioRepository).incrementarIntentosFallidos(anyString());

        usuarioService.incrementarIntentosFallidos("admin");
        verify(usuarioRepository, times(1)).incrementarIntentosFallidos("admin");
    }

    @Test
    public void testBloquearUsuario() {
        doNothing().when(usuarioRepository).bloquearUsuario(anyString());

        usuarioService.bloquearUsuario("admin");
        verify(usuarioRepository, times(1)).bloquearUsuario("admin");
    }
}
