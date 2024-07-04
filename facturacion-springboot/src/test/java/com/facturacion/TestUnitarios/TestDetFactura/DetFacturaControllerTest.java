package com.facturacion.TestUnitarios.TestDetFactura;

import com.facturacion.controller.DetFacturaController;
import com.facturacion.dto.DetFacturaDTO;
import com.facturacion.service.DetFacturaService;
import com.facturacion.util.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(DetFacturaController.class)
public class DetFacturaControllerTest {

    @MockBean
    private DetFacturaService detFacturaService;

    @InjectMocks
    private DetFacturaController detFacturaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGuardarDetallesFactura() throws Exception {
        DetFacturaDTO detFacturaDTO = new DetFacturaDTO(1, 2, 3);
        doNothing().when(detFacturaService).insertarFacturas(Arrays.asList(detFacturaDTO));

        mockMvc.perform(post("/det-factura/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"codigoProducto\": 1, \"cantidad\": 2, \"pkCabFactura\": 3}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value("Detalles de factura guardados exitosamente"));
    }
}
