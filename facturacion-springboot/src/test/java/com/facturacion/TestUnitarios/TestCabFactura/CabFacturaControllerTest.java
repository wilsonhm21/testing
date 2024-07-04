package com.facturacion.TestUnitarios.TestCabFactura;

import com.facturacion.controller.CabFacturaController;
import com.facturacion.entity.CabFactura;
import com.facturacion.service.CabFacturaService;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CabFacturaController.class)
public class CabFacturaControllerTest {

    @MockBean
    private CabFacturaService cabFacturaService;

    @InjectMocks
    private CabFacturaController cabFacturaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testObtenerTodasCabeceras() throws Exception {
        List<CabFactura> cabFacturas = Arrays.asList(new CabFactura(), new CabFactura());
        when(cabFacturaService.obtenerTodas()).thenReturn(cabFacturas);

        mockMvc.perform(get("/cab-factura")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testObtenerFacturaPorId() throws Exception {
        CabFactura cabFactura = new CabFactura();
        cabFactura.setIdFcatura(1);
        when(cabFacturaService.obtenerPorId(anyInt())).thenReturn(Optional.of(cabFactura));

        mockMvc.perform(get("/cab-factura/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFcatura").exists());
    }

    @Test
    public void testObtenerFacturaPorIdNotFound() throws Exception {
        when(cabFacturaService.obtenerPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/cab-factura/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGuardarFactura() throws Exception {
        CabFactura cabFactura = new CabFactura();
        cabFactura.setIdFcatura(1);
        when(cabFacturaService.guardarCabFactura(any(CabFactura.class))).thenReturn(cabFactura);

        mockMvc.perform(post("/cab-factura")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroFactura\": \"123\", \"rucCliente\": \"123456789\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idFcatura").value(1));
    }

    @Test
    public void testEliminarFacturaPorId() throws Exception {
        doNothing().when(cabFacturaService).eliminarPorId(anyInt());

        mockMvc.perform(delete("/cab-factura/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGeneraFactura() throws Exception {
        when(cabFacturaService.generaFactura()).thenReturn(100);

        mockMvc.perform(get("/cab-factura/genera-factura")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(100));
    }
}
