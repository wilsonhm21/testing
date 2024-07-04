package com.facturacion.TestUnitarios.TestDetFactura;

import com.facturacion.dto.DetFacturaDTO;
import com.facturacion.entity.DetFactura;
import com.facturacion.repository.CabFacturaRepository;
import com.facturacion.repository.DetFacturaRepository;
import com.facturacion.service.DetFacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetFacturaServiceTest {

    @Mock
    private DetFacturaRepository detFacturaRepository;

    @Mock
    private CabFacturaRepository cabFacturaRepository;

    @InjectMocks
    private DetFacturaService detFacturaService;

    private DetFacturaDTO detFacturaDTO;

    @BeforeEach
    public void setUp() {
        detFacturaDTO = new DetFacturaDTO(1, 2, 3);
    }

    @Test
    public void testInsertarFacturas() {
        doNothing().when(detFacturaRepository).insertarFactura(anyInt(), anyInt(), anyInt());

        detFacturaService.insertarFacturas(Arrays.asList(detFacturaDTO));

        verify(detFacturaRepository, times(1)).insertarFactura(detFacturaDTO.getCodigoProducto(), detFacturaDTO.getCantidad(), detFacturaDTO.getPkCabFactura());
    }
}
