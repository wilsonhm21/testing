package com.facturacion.TestIntegracion.TestDetFactura;


import com.facturacion.dto.DetFacturaDTO;
import com.facturacion.entity.CabFactura;
import com.facturacion.entity.DetFactura;
import com.facturacion.repository.CabFacturaRepository;
import com.facturacion.repository.DetFacturaRepository;
import com.facturacion.service.DetFacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class DetFacturaServiceIntegrationTest {

    @Autowired
    private DetFacturaService detFacturaService;

    @Autowired
    private DetFacturaRepository detFacturaRepository;

    @Autowired
    private CabFacturaRepository cabFacturaRepository;

    @BeforeEach
    public void setUp() {
        detFacturaRepository.deleteAll();
        cabFacturaRepository.deleteAll();
    }

    @Test
    public void testInsertarFacturas() {
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

        detFacturaService.insertarFacturas(detFacturaDTOs);

        List<DetFactura> detFacturas = (List<DetFactura>) detFacturaRepository.findAll();
        assertEquals(2, detFacturas.size());

        DetFactura detFactura1 = detFacturas.get(0);
        assertEquals(1, detFactura1.getCodigoProducto());
        assertEquals(5, detFactura1.getCantidad());
        assertEquals(facturaGuardada.getIdFcatura(), detFactura1.getPkCabFactura().getIdFcatura());

        DetFactura detFactura2 = detFacturas.get(1);
        assertEquals(2, detFactura2.getCodigoProducto());
        assertEquals(10, detFactura2.getCantidad());
        assertEquals(facturaGuardada.getIdFcatura(), detFactura2.getPkCabFactura().getIdFcatura());
    }
}
