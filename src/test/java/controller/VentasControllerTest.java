package controller;

import model.business.cine.Sala;
import model.business.negocio.TarjetaDescuento;
import model.business.pelicula.Pelicula;
import model.constants.TipoGenero;
import model.constants.TipoTarjeta;
import model.dto.FuncionDTO;
import model.dto.VentaDTO;
import model.exception.CinemaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VentasControllerTest {
    private VentasController ventasController;

    @BeforeEach
    public void setUp() {
        FuncionController.eliminarInstancia();
        VentasController.eliminarInstancia();
        ventasController = VentasController.obtenerInstancia();
    }

    @Test
    public void testRegistrarVenta() throws CinemaException {
        FuncionDTO funcionDTO = obtenerFuncionDTO();
        FuncionController.obtenerInstancia().registrarFuncionPorGenero(funcionDTO);
        ventasController.registrarVenta(crearVentaDTO(funcionDTO));
        assertEquals(1, ventasController.getVentas().size());
    }

    @Test
    public void testRegistrarVentaSinFuncionesExcepcion() {
        FuncionDTO funcionDTO = obtenerFuncionDTO();
        assertThrows(CinemaException.class, () -> ventasController.registrarVenta(crearVentaDTO(funcionDTO)));
    }

    @Test
    public void testCalcularRecaudacionPorPelicula() throws CinemaException {
        FuncionDTO funcionDTO = obtenerFuncionDTO();
        FuncionController.obtenerInstancia().registrarFuncionPorGenero(funcionDTO);
        ventasController.registrarVenta(crearVentaDTO(funcionDTO));
        if(LocalDate.now().getDayOfWeek().getValue() <= 3){
            assertEquals(380, ventasController.recaudacionPorPelicula(0));
        } else {
            assertEquals(1000, ventasController.recaudacionPorPelicula(0));
        }
    }

    @Test
    public void testCalcularRecaudacionVariasFunciones() throws CinemaException {
        FuncionDTO funcionDTO = obtenerFuncionDTO();
        FuncionDTO funcionDTOII = obtenerFuncionDTOII();

        FuncionController.obtenerInstancia().registrarFuncionPorGenero(funcionDTO);
        FuncionController.obtenerInstancia().registrarFuncionPorGenero(funcionDTOII);

        ventasController.registrarVenta(crearVentaDTO(funcionDTO));
        ventasController.registrarVenta(crearVentaDTOII(funcionDTOII));
        if(LocalDate.now().getDayOfWeek().getValue() <= 3){
            assertEquals(880, ventasController.recaudacionPorPelicula(0));
        } else {
            assertEquals(2000, ventasController.recaudacionPorPelicula(0));
        }
    }

    @Test
    public void testRegistrarVentaTiraErrorAsientoOcupado() throws CinemaException {
        FuncionDTO funcionDTO = obtenerFuncionDTO();
        FuncionController.obtenerInstancia().registrarFuncionPorGenero(funcionDTO);
        ventasController.registrarVenta(crearVentaDTO(funcionDTO));
        assertThrows(CinemaException.class, () -> ventasController.registrarVenta(crearVentaDTO(funcionDTO)));
    }


    private VentaDTO crearVentaDTO(FuncionDTO funcionDTO) {
        Date fecha = new Date();
        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setFchVenta(fecha);
        ventaDTO.setFuncionDTO(funcionDTO);
        ventaDTO.setAsientos(5);
        ventaDTO.setAsientosSeleccionados(List.of(1, 2, 3, 4, 5));
        ventaDTO.setTarjetaDescuento(tarjetaDescuento());
        return ventaDTO;
    }

    private VentaDTO crearVentaDTOII(FuncionDTO funcionDTO) {
        Date fecha = new Date();
        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setFchVenta(fecha);
        ventaDTO.setFuncionDTO(funcionDTO);
        ventaDTO.setAsientos(5);
        ventaDTO.setAsientosSeleccionados(List.of(10, 11, 12, 13, 14));
        ventaDTO.setTarjetaDescuento(tarjetaDescuentoNull());
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

    private FuncionDTO obtenerFuncionDTOII() {
        LocalDate localDate = LocalDate.now().plusMonths(1);
        Date fecha = java.sql.Date.valueOf(localDate);
        FuncionDTO funcionDTO = new FuncionDTO(
                obtenerPelicula(),
                "16:00", fecha,
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

    private TarjetaDescuento tarjetaDescuentoNull() {
        TarjetaDescuento tarjeta = new TarjetaDescuento();
        tarjeta.setNumeroTarjeta(null);
        tarjeta.setTarjetaID(0);
        tarjeta.setTipoTarjeta(TipoTarjeta.SIN_DESCUENTO);
        return tarjeta;
    }

    private TarjetaDescuento tarjetaDescuento() {
        TarjetaDescuento tarjeta = new TarjetaDescuento();
        tarjeta.setNumeroTarjeta("356");
        tarjeta.setTarjetaID(54);
        tarjeta.setTipoTarjeta(TipoTarjeta.PAMI);
        return tarjeta;
    }

    private Sala obtenerSala() {
        return new Sala(6, "Sala 6", 100);
    }
}
