package com.facturacion.TestIntegracion.TestUsuarios;

import com.facturacion.entity.Usuario;
import com.facturacion.repository.UsuarioRepository;
import com.facturacion.util.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    @Transactional
    public void setup() {
        // Eliminar cualquier usuario existente con el mismo nombre para evitar conflictos
        usuarioRepository.deleteAll();

        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername("newuser");
        usuario.setPassword("password123");
        usuario.setIntentosFallidos(0);
        usuario.setBloqueado((byte) 0);
        usuarioRepository.save(usuario);
    }

    @Test
    public void testLoginSuccessful() {
        Map<String, String> params = new HashMap<>();
        params.put("username", "newuser");
        params.put("password", "password123");

        ResponseEntity<ResponseMessage> response = restTemplate.getForEntity("/facturacion/login?username={username}&password={password}", ResponseMessage.class, params);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(200);
        assertThat(response.getBody().getData()).isEqualTo("Usuario autenticado exitosamente");
    }

    @Test
    public void testLoginInvalidCredentials() {
        Map<String, String> params = new HashMap<>();
        params.put("username", "newuser");
        params.put("password", "wrongpassword");

        ResponseEntity<ResponseMessage> response = restTemplate.getForEntity("/facturacion/login?username={username}&password={password}", ResponseMessage.class, params);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(401);
        assertThat(response.getBody().getData()).isEqualTo("Credenciales incorrectas");
    }

    @Test
    public void testLoginUserLocked() {
        Map<String, String> params = new HashMap<>();
        params.put("username", "newuser");
        params.put("password", "wrongpassword");

        // Attempt to login three times with wrong password to lock the account
        restTemplate.getForEntity("/facturacion/login?username={username}&password={password}", ResponseMessage.class, params);
        restTemplate.getForEntity("/facturacion/login?username={username}&password={password}", ResponseMessage.class, params);
        restTemplate.getForEntity("/facturacion/login?username={username}&password={password}", ResponseMessage.class, params);

        // Final attempt after locking
        ResponseEntity<ResponseMessage> response = restTemplate.getForEntity("/facturacion/login?username={username}&password={password}", ResponseMessage.class, params);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(401);
        assertThat(response.getBody().getData()).isEqualTo("Usuario bloqueado: superó los intentos permitidos");
    }

    @Test
    public void testLoginWithMissingCredentials() {
        ResponseEntity<ResponseMessage> response = restTemplate.getForEntity("/facturacion/login?username=&password=", ResponseMessage.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(400);
        assertThat(response.getBody().getData()).isEqualTo("Falta el usuario o la contraseña");
    }
}