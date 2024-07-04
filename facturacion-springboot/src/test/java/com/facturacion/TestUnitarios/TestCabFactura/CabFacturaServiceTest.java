package com.facturacion.TestUnitarios.TestCabFactura;

import com.facturacion.entity.CabFactura;
import com.facturacion.repository.CabFacturaRepository;
import com.facturacion.service.CabFacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CabFacturaServiceTest {

    @Mock
    private CabFacturaRepository cabFacturaRepository;

    @InjectMocks
    private CabFacturaService cabFacturaService;

    private CabFactura cabFactura;

    @BeforeEach
    public void setUp() {
        cabFactura = new CabFactura();
        cabFactura.setIdFcatura(1);
        cabFactura.setNumeroFactura(123);
        cabFactura.setRucCliente("123456789");
    }

    @Test
    public void testGuardarCabFactura() {
        when(cabFacturaRepository.save(any(CabFactura.class))).thenReturn(cabFactura);

        CabFactura result = cabFacturaService.guardarCabFactura(cabFactura);
        assertEquals(cabFactura.getIdFcatura(), result.getIdFcatura());
        verify(cabFacturaRepository, times(1)).save(cabFactura);
    }

    @Test
    public void testObtenerTodas() {
        List<CabFactura> cabFacturas = Arrays.asList(cabFactura, cabFactura);
        when(cabFacturaRepository.findAll()).thenReturn(cabFacturas);

        List<CabFactura> result = cabFacturaService.obtenerTodas();
        assertEquals(2, result.size());
        verify(cabFacturaRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerPorId() {
        when(cabFacturaRepository.findById(anyInt())).thenReturn(Optional.of(cabFactura));

        Optional<CabFactura> result = cabFacturaService.obtenerPorId(1);
        assertTrue(result.isPresent());
        assertEquals(cabFactura.getIdFcatura(), result.get().getIdFcatura());
        verify(cabFacturaRepository, times(1)).findById(1);
    }

    @Test
    public void testEliminarPorId() {
        doNothing().when(cabFacturaRepository).deleteById(anyInt());

        cabFacturaService.eliminarPorId(1);
        verify(cabFacturaRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGeneraFactura() {
        when(cabFacturaRepository.generaFactura()).thenReturn(100);

        Integer result = cabFacturaService.generaFactura();
        assertEquals(100, result);
        verify(cabFacturaRepository, times(1)).generaFactura();
    }
}
