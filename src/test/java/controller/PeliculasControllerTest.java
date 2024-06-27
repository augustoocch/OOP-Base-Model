package controller;

import model.business.pelicula.Pelicula;
import model.constants.TipoGenero;
import model.dto.PeliculaDTO;
import model.exception.CinemaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeliculasControllerTest {

    private PeliculasController peliculasController;

    @BeforeEach
    public void setUp() {
        peliculasController = PeliculasController.obtenerInstancia();
    }

    @Test
    public void testAgregarPelicula() throws CinemaException {
        peliculasController.registrarPelicula(obtenerPeliculaDTO());
        assertEquals(1, peliculasController.getPeliculas().size());
    }

    @Test
    public void testConsultarPeliculaGenero() throws CinemaException {
        peliculasController.registrarPelicula(obtenerPeliculaDTO());

        List<Pelicula> peliculas = peliculasController.obtenerPeliculasPorGenero(TipoGenero.Drama);
        assertEquals(1, peliculas.size());
        assertEquals("Titanic", peliculas.get(0).getNombrePelicula());
        assertEquals("Jacobo Mark", peliculas.get(0).getDirector());
        assertEquals(195, peliculas.get(0).getDuracionEnMinutos());
        assertEquals(TipoGenero.Drama, peliculas.get(0).getGeneroID());
    }

    private PeliculaDTO obtenerPeliculaDTO() {
        List<String> actores = List.of("Leonardo DiCaprio", "Kate Winslet");
        PeliculaDTO peliculaDTO = new PeliculaDTO();
        peliculaDTO.setNombrePelicula("Titanic");
        peliculaDTO.setActores(actores);
        peliculaDTO.setDirector("Jacobo Mark");
        peliculaDTO.setGeneroID(TipoGenero.Drama);
        peliculaDTO.setDuracionEnMinutos(195);
        return peliculaDTO;
    }
}
