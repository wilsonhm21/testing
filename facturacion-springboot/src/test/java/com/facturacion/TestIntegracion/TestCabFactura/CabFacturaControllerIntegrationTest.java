package com.facturacion.TestIntegracion.TestCabFactura;

import com.facturacion.entity.CabFactura;
import com.facturacion.repository.CabFacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CabFacturaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CabFacturaRepository cabFacturaRepository;

    @BeforeEach
    public void setUp() {
        cabFacturaRepository.deleteAll();
    }

    @Test
    public void testGuardarFactura() throws Exception {
        mockMvc.perform(post("/cab-factura")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroFactura\":123,\"rucCliente\":\"123456789\",\"subtotal\":\"100.00\",\"igv\":\"18.00\",\"total\":\"118.00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroFactura").value(123));
    }

    @Test
    public void testObtenerFacturaPorId() throws Exception {
        CabFactura cabFactura = new CabFactura();
        cabFactura.setNumeroFactura(123);
        cabFactura.setRucCliente("123456789");
        cabFactura.setSubtotal("100.00");
        cabFactura.setIgv("18.00");
        cabFactura.setTotal("118.00");
        cabFactura = cabFacturaRepository.save(cabFactura);

        mockMvc.perform(get("/cab-factura/" + cabFactura.getIdFcatura())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroFactura").value(123));
    }

    @Test
    public void testEliminarFacturaPorId() throws Exception {
        CabFactura cabFactura = new CabFactura();
        cabFactura.setNumeroFactura(123);
        cabFactura.setRucCliente("123456789");
        cabFactura.setSubtotal("100.00");
        cabFactura.setIgv("18.00");
        cabFactura.setTotal("118.00");
        cabFactura = cabFacturaRepository.save(cabFactura);

        mockMvc.perform(delete("/cab-factura/" + cabFactura.getIdFcatura())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testObtenerTodasCabeceras() throws Exception {
        CabFactura cabFactura = new CabFactura();
        cabFactura.setNumeroFactura(123);
        cabFactura.setRucCliente("123456789");
        cabFactura.setSubtotal("100.00");
        cabFactura.setIgv("18.00");
        cabFactura.setTotal("118.00");
        cabFacturaRepository.save(cabFactura);

        mockMvc.perform(get("/cab-factura")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroFactura").value(123));
    }
}
