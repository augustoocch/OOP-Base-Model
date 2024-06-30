package controller;

import model.business.cine.Funcion;
import model.business.cine.Sala;
import model.business.pelicula.Pelicula;
import model.constants.TipoGenero;
import model.dto.FuncionDTO;
import model.exception.CinemaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncionControllerTest {
    private FuncionController funcionController;

    @BeforeEach
    public void setUp() {
        funcionController = FuncionController.obtenerInstancia();
    }

    @Test
    public void testCrearNuevaFuncion() throws CinemaException {
        LocalDate localDate = LocalDate.now().plusMonths(1);
        Date fecha = java.sql.Date.valueOf(localDate);

        FuncionDTO funcionDTO = obtenerFuncionDTO();
        funcionController.registrarFuncionPorGenero(funcionDTO);

        List<FuncionDTO> funciones = funcionController.getListaFuncionesPorFecha(fecha);
        assertEquals(1, funciones.size());
    }

    @Test
    public void testCrearNuevaFuncionExistenteArrojaError() throws CinemaException {
        LocalDate localDate = LocalDate.now().plusMonths(1);
        Date fecha = java.sql.Date.valueOf(localDate);

        FuncionDTO funcionDTO = obtenerFuncionDTO();
        funcionController.registrarFuncionPorGenero(funcionDTO);
        try {
            funcionController.registrarFuncionPorGenero(funcionDTO);
        } catch (CinemaException e) {
            assertEquals("Funcion ya existente", e.getMessage());
        }
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
