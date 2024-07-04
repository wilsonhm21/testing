package com.facturacion.TestUnitarios.TestProductos;

import com.facturacion.controller.ProductoController;
import com.facturacion.dto.DetFacturaDTO;
import com.facturacion.entity.Producto;
import com.facturacion.service.ProductoService;
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
@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @MockBean
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    @Test
    public void testListarProducto() throws Exception {
        List<Producto> productos = Arrays.asList(new Producto(), new Producto());
        when(productoService.listarProducto()).thenReturn(productos);

        mockMvc.perform(get("/productos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testObtenerProductoPorId() throws Exception {
        Producto producto = new Producto();
        producto.setIdProducto(1); // Asegúrate de que el campo idProducto esté configurado
        when(productoService.obtenerProductoPorId(anyInt())).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/productos/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").exists());
    }

    @Test
    public void testObtenerProductoPorIdNotFound() throws Exception {
        when(productoService.obtenerProductoPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/productos/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testVerificarSiExiteElCodProducto() throws Exception {
        when(productoService.verificarSiExiteElCodProducto(anyString())).thenReturn("codigo");

        mockMvc.perform(get("/productos/verificar-cod-producto/{cod_producto}", "codigo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value("codigo"));
    }

    @Test
    public void testDisminuirStock() throws Exception {
        doNothing().when(productoService).disminuirStock(any(List.class));

        mockMvc.perform(post("/productos/disminuir-stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"codigoProducto\": \"1\", \"cantidad\": 1}]")) // Asegúrate de que los valores sean del tipo correcto
                .andExpect(status().isOk());
    }

    @Test
    public void testCrearProducto() throws Exception {
        Producto producto = new Producto();
        producto.setIdProducto(1);
        when(productoService.crearProducto(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/productos/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codigo\": \"codigo\", \"nombre\": \"nombre\", \"precio\": 10.0, \"stock\": 100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProducto").value(1));
    }

    @Test
    public void testActualizarProducto() throws Exception {
        doNothing().when(productoService).actualizarProducto(any(Producto.class));

        mockMvc.perform(put("/productos/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idProducto\": 1, \"codigo\": \"codigo\", \"nombre\": \"nombre\", \"precio\": 10.0, \"stock\": 100}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEliminarProducto() throws Exception {
        when(productoService.obtenerProductoPorId(anyInt())).thenReturn(Optional.of(new Producto()));
        doNothing().when(productoService).eliminarProducto(anyInt());

        mockMvc.perform(delete("/productos/eliminar/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testEliminarProductoNotFound() throws Exception {
        when(productoService.obtenerProductoPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/productos/eliminar/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
