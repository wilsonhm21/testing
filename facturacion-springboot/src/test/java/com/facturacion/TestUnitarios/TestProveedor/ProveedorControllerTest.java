package com.facturacion.TestUnitarios.TestProveedor;

import com.facturacion.controller.ProveedorController;
import com.facturacion.entity.Proveedor;
import com.facturacion.service.ProveedorService;
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
@WebMvcTest(ProveedorController.class)
public class ProveedorControllerTest {

    @MockBean
    private ProveedorService proveedorService;

    @InjectMocks
    private ProveedorController proveedorController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(proveedorController).build();
    }

    @Test
    public void testListarProveedor() throws Exception {
        List<Proveedor> proveedores = Arrays.asList(new Proveedor(), new Proveedor());
        when(proveedorService.listarProveedor()).thenReturn(proveedores);

        mockMvc.perform(get("/proveedor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testObtenerProveedorPorId() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(1); // Asegúrate de que el campo idProveedor esté configurado
        when(proveedorService.obtenerProveedorPorId(anyInt())).thenReturn(Optional.of(proveedor));

        mockMvc.perform(get("/proveedor/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProveedor").exists());
    }

    @Test
    public void testObtenerProveedorPorIdNotFound() throws Exception {
        when(proveedorService.obtenerProveedorPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/proveedor/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testVerificarSiExiteProveedor() throws Exception {
        when(proveedorService.verificarSiExiteProveedor(anyString())).thenReturn("1");

        mockMvc.perform(get("/proveedor/verificar-proveedor/{ruc}", "123456789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value("1"));
    }

    @Test
    public void testCrearProveedor() throws Exception {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(1);
        when(proveedorService.crearProveedor(any(Proveedor.class))).thenReturn(proveedor);

        mockMvc.perform(post("/proveedor/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ruc\": \"123456789\", \"nombre\": \"nombre\", \"direccion\": \"direccion\", \"correo\": \"correo@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProveedor").value(1));
    }

    @Test
    public void testActualizarProveedor() throws Exception {
        when(proveedorService.crearProveedor(any(Proveedor.class))).thenReturn(new Proveedor());

        mockMvc.perform(put("/proveedor/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idProveedor\": 1, \"ruc\": \"123456789\", \"nombre\": \"nombre\", \"direccion\": \"direccion\", \"correo\": \"correo@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEliminarProveedor() throws Exception {
        when(proveedorService.obtenerProveedorPorId(anyInt())).thenReturn(Optional.of(new Proveedor()));
        doNothing().when(proveedorService).eliminarProveedor(anyInt());

        mockMvc.perform(delete("/proveedor/eliminar/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testEliminarProveedorNotFound() throws Exception {
        when(proveedorService.obtenerProveedorPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/proveedor/eliminar/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
