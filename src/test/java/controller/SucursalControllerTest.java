package controller;

import model.business.cine.Sala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SucursalControllerTest {

    private SucursalController sucursalController;

    @BeforeEach
    public void setUp() {
        sucursalController = SucursalController.obtenerInstancia();
    }

    @Test
    public void testObtenerSalaPorDenominacion() {
        Sala sala = sucursalController.obtenerSalaPorDenominacion("Sala 4");

        assertNotNull(sala);
        assertEquals("Sala 4", sala.getDenominacion());
        assertEquals(200, sala.getAsientos());
    }
}
