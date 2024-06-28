package controller;

import model.business.cine.Sala;
import model.business.pelicula.Pelicula;
import model.constants.TipoGenero;
import model.dto.FuncionDTO;
import model.dto.VentaDTO;
import model.exception.CinemaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VentasControllerTest {

    private VentasController ventasController;


    @BeforeEach
    public void setUp() {
        ventasController = VentasController.obtenerInstancia();
    }


    @Test
    public void testRecaudacionPorPelicula() {

    }

    @Test
    public void testRegistrarVenta() throws CinemaException {
        LocalDate localDate = LocalDate.now().plusMonths(1);
        Date fecha = java.sql.Date.valueOf(localDate);
        FuncionDTO funcionDTO = new FuncionDTO(
                obtenerPelicula(),
                "20:00", fecha,
                obtenerSala());
        FuncionController.obtenerInstancia().registrarFuncionPorGenero(funcionDTO);
        ventasController.registrarVenta(crearVentaDTO());
        assertEquals(1, ventasController.getVentas().size());
    }

    @Test
    public void testRegistrarVentaSinFuncionesExcepcion() {
        try {
            ventasController.registrarVenta(crearVentaDTO());
        } catch (CinemaException e) {
            assertEquals("Funciones no encontradas", e.getMessage());
        }
    }

    @Test
    public void testCalcularRecaudacionPorPelicula() throws CinemaException {
        LocalDate localDate = LocalDate.now().plusMonths(1);
        Date fecha = java.sql.Date.valueOf(localDate);
        FuncionDTO funcionDTO = new FuncionDTO(
                obtenerPelicula(),
                "20:00", fecha,
                obtenerSala());
        FuncionController.obtenerInstancia().registrarFuncionPorGenero(funcionDTO);
        ventasController.registrarVenta(crearVentaDTO());
        assertEquals(1, ventasController.recaudacionPorPelicula(0));
    }


    private VentaDTO crearVentaDTO() {
        Date fecha = new Date();
        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setFchVenta(fecha);
        ventaDTO.setFuncionDTO(obtenerFuncionDTO());
        ventaDTO.setAsientos(5);
        ventaDTO.setCombos(null);
        ventaDTO.setTarjetaDescuento(null);
        return ventaDTO;
    }


    private FuncionDTO obtenerFuncionDTO() {
        LocalDate localDate = LocalDate.now().plusMonths(1);
        Date fecha = java.sql.Date.valueOf(localDate);
        FuncionDTO funcionDTO = new FuncionDTO(
                obtenerPelicula(),
                "20:00", fecha,
                obtenerSala());
        return funcionDTO;
    }

    private Pelicula obtenerPelicula() {
        List<String> actores = List.of("Leonardo DiCaprio", "Kate Winslet");
        Pelicula pelicula = new Pelicula();
        pelicula.setNombrePelicula("Titanic");
        pelicula.setActores(actores);
        pelicula.setDirector("Jacobo Mark");
        pelicula.setGeneroID(TipoGenero.Drama);
        pelicula.setDuracionEnMinutos(195);
        return pelicula;
    }

    private Sala obtenerSala() {
        return new Sala(6, "Sala 6", 100);
    }
}
