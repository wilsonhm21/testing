package com.facturacion.TestIntegracion.TestCabFactura;


import com.facturacion.entity.CabFactura;
import com.facturacion.repository.CabFacturaRepository;
import com.facturacion.service.CabFacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CabFacturaServiceIntegrationTest {

    @Autowired
    private CabFacturaService cabFacturaService;

    @Autowired
    private CabFacturaRepository cabFacturaRepository;

    @BeforeEach
    public void setUp() {
        cabFacturaRepository.deleteAll();
    }

    @Test
    public void testGuardarCabFactura() {
        CabFactura cabFactura = new CabFactura();
        cabFactura.setNumeroFactura(123);
        cabFactura.setRucCliente("123456789");
        cabFactura.setSubtotal("100.00");
        cabFactura.setIgv("18.00");
        cabFactura.setTotal("118.00");

        CabFactura facturaGuardada = cabFacturaService.guardarCabFactura(cabFactura);
        assertNotNull(facturaGuardada.getIdFcatura());
    }

    @Test
    public void testObtenerPorId() {
        CabFactura cabFactura = new CabFactura();
        cabFactura.setNumeroFactura(123);
        cabFactura.setRucCliente("123456789");
        cabFactura.setSubtotal("100.00");
        cabFactura.setIgv("18.00");
        cabFactura.setTotal("118.00");

        CabFactura facturaGuardada = cabFacturaService.guardarCabFactura(cabFactura);
        Optional<CabFactura> facturaObtenida = cabFacturaService.obtenerPorId(facturaGuardada.getIdFcatura());

        assertTrue(facturaObtenida.isPresent());
        assertEquals(facturaGuardada.getIdFcatura(), facturaObtenida.get().getIdFcatura());
    }

    @Test
    public void testEliminarPorId() {
        CabFactura cabFactura = new CabFactura();
        cabFactura.setNumeroFactura(123);
        cabFactura.setRucCliente("123456789");
        cabFactura.setSubtotal("100.00");
        cabFactura.setIgv("18.00");
        cabFactura.setTotal("118.00");

        CabFactura facturaGuardada = cabFacturaService.guardarCabFactura(cabFactura);
        Integer id = facturaGuardada.getIdFcatura();
        cabFacturaService.eliminarPorId(id);

        Optional<CabFactura> facturaObtenida = cabFacturaService.obtenerPorId(id);
        assertFalse(facturaObtenida.isPresent());
    }

    @Test
    public void testObtenerTodas() {
        CabFactura cabFactura1 = new CabFactura();
        cabFactura1.setNumeroFactura(123);
        cabFactura1.setRucCliente("123456789");
        cabFactura1.setSubtotal("100.00");
        cabFactura1.setIgv("18.00");
        cabFactura1.setTotal("118.00");

        CabFactura cabFactura2 = new CabFactura();
        cabFactura2.setNumeroFactura(124);
        cabFactura2.setRucCliente("987654321");
        cabFactura2.setSubtotal("200.00");
        cabFactura2.setIgv("36.00");
        cabFactura2.setTotal("236.00");

        cabFacturaService.guardarCabFactura(cabFactura1);
        cabFacturaService.guardarCabFactura(cabFactura2);

        List<CabFactura> todasFacturas = cabFacturaService.obtenerTodas();
        assertEquals(2, todasFacturas.size());
    }

    @Test
    public void testGeneraFactura() {
        Integer numFactura = cabFacturaService.generaFactura();
        assertNotNull(numFactura);
    }
}
