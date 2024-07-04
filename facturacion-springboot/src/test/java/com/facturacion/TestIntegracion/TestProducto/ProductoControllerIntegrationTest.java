package com.facturacion.TestIntegracion.TestProducto;


import com.facturacion.entity.Producto;
import com.facturacion.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    public void setup() {
        productoRepository.deleteAll();
    }

    @Test
    public void testCrearProducto() throws Exception {
        Producto producto = new Producto();
        producto.setCodigo("P001");
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);
        producto.setStock(50.0);

        mockMvc.perform(post("/productos/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codigo\":\"P001\",\"nombre\":\"Producto Test\",\"precio\":100.0,\"stock\":50.0}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value("P001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Producto Test"));
    }

    @Test
    public void testListarProductos() throws Exception {
        Producto producto = new Producto();
        producto.setCodigo("P001");
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);
        producto.setStock(50.0);
        productoRepository.save(producto);

        mockMvc.perform(get("/productos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].codigo").value("P001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Producto Test"));
    }
}