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

    @Test
    public void testConsultarPeliculaPorNombre() throws CinemaException {
        peliculasController.registrarPelicula(obtenerPeliculaDTO());
        peliculasController.registrarPelicula(obtenerPeliculaDTO2());

        Pelicula pelicula = peliculasController.obtenerPeliculaPorNombre("La caida del halcon");
        assertEquals("La caida del halcon", pelicula.getNombrePelicula());
        assertEquals("Jacobo Mark", pelicula.getDirector());
        assertEquals(132, pelicula.getDuracionEnMinutos());
        assertEquals(TipoGenero.Suspenso, pelicula.getGeneroID());
    }

    @Test
    public void testAgregarPeliculaExistenteArrojaError() throws CinemaException {
        peliculasController.registrarPelicula(obtenerPeliculaDTO());
        try {
            peliculasController.registrarPelicula(obtenerPeliculaDTO());
        } catch (CinemaException e) {
            assertEquals("Pelicula ya existente", e.getMessage());
        }
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

    private PeliculaDTO obtenerPeliculaDTO2() {
        List<String> actores = List.of("Javier Bardem", "Penelope Cruz");
        PeliculaDTO peliculaDTO = new PeliculaDTO();
        peliculaDTO.setNombrePelicula("La caida del halcon");
        peliculaDTO.setActores(actores);
        peliculaDTO.setDirector("Jacobo Mark");
        peliculaDTO.setGeneroID(TipoGenero.Suspenso);
        peliculaDTO.setDuracionEnMinutos(132);
        return peliculaDTO;
    }
}
