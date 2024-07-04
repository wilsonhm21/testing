package com.facturacion.TestUnitarios.TestUsuarios;

import com.facturacion.controller.UsuarioController;
import com.facturacion.service.UsuarioService;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @MockBean
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        when(usuarioService.estaBloqueado(anyString())).thenReturn(false);
        when(usuarioService.buscarPorUsuarioYContrasenia(anyString(), anyString()))
                .thenReturn(Optional.of(new Object[] {"usuarioAutenticado"})); // Simula que el usuario se autenticó correctamente

        mockMvc.perform(get("/facturacion/login")
                        .param("username", "admin")
                        .param("password", "admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data").value("Usuario autenticado exitosamente"));
    }

    @Test
    public void testLoginBlockedUser() throws Exception {
        when(usuarioService.estaBloqueado(anyString())).thenReturn(true);

        mockMvc.perform(get("/facturacion/login")
                        .param("username", "admin")
                        .param("password", "admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(HttpStatus.FORBIDDEN.value()))
                .andExpect(jsonPath("$.data").value("Usuario bloqueado"));
    }

    @Test
    public void testLoginInvalidCredentials() throws Exception {
        when(usuarioService.estaBloqueado(anyString())).thenReturn(false);
        when(usuarioService.buscarPorUsuarioYContrasenia(anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(usuarioService.obtenerIntentosFallidos(anyString())).thenReturn(Optional.of(1));

        mockMvc.perform(get("/facturacion/login")
                        .param("username", "admin")
                        .param("password", "wrongpassword")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(jsonPath("$.data").value("Credenciales incorrectas"));
    }

    @Test
    public void testLoginMissingCredentials() throws Exception {
        mockMvc.perform(get("/facturacion/login")
                        .param("username", "")
                        .param("password", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.data").value("Falta el usuario o la contraseña"));
    }
}
