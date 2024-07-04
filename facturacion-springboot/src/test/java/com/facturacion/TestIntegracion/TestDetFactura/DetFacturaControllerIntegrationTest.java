package com.facturacion.TestIntegracion.TestDetFactura;

import com.facturacion.dto.DetFacturaDTO;
import com.facturacion.entity.CabFactura;
import com.facturacion.repository.CabFacturaRepository;
import com.facturacion.repository.DetFacturaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DetFacturaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CabFacturaRepository cabFacturaRepository;

    @Autowired
    private DetFacturaRepository detFacturaRepository;

    @BeforeEach
    public void setUp() {
        detFacturaRepository.deleteAll();
        cabFacturaRepository.deleteAll();
    }

    @Test
    public void testGuardarDetallesFactura() throws Exception {
        CabFactura cabFactura = new CabFactura();
        cabFactura.setNumeroFactura(123);
        cabFactura.setRucCliente("123456789");
        cabFactura.setSubtotal("100.00");
        cabFactura.setIgv("18.00");
        cabFactura.setTotal("118.00");

        CabFactura facturaGuardada = cabFacturaRepository.save(cabFactura);

        List<DetFacturaDTO> detFacturaDTOs = new ArrayList<>();
        detFacturaDTOs.add(new DetFacturaDTO(1, 5, facturaGuardada.getIdFcatura()));
        detFacturaDTOs.add(new DetFacturaDTO(2, 10, facturaGuardada.getIdFcatura()));

        mockMvc.perform(post("/det-factura/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detFacturaDTOs)))
                .andExpect(status().isOk());
    }
}
