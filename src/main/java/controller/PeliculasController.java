package controller;

import config.CineConfig;
import lombok.Getter;
import lombok.Setter;
import model.business.pelicula.Pelicula;
import model.constants.TipoGenero;
import model.dto.PeliculaDTO;
import model.exception.CinemaException;
import model.mapper.PeliculaMapper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static model.exception.ErrorCode.PELICULA_YA_EXISTENTE;

@Getter
@Setter
public class PeliculasController {

    private AtomicInteger id;
	private List<Pelicula> peliculas;
    private static PeliculasController instancia;
    private PeliculaMapper peliculaMapper;
    private CineConfig config;
    private List<String> actores;
	
    private PeliculasController() {
    	peliculas= new ArrayList<Pelicula>();
        id = new AtomicInteger(0);
        peliculaMapper = new PeliculaMapper();
        config = CineConfig.getInstance();
        actores = config.getListaActores();
    }

    public static PeliculasController obtenerInstancia() {
        if(instancia == null){
            instancia = new PeliculasController();
        }
        return instancia;
    }

    public Pelicula obtenerPeliculaPorNombre(String nombre) {
        return peliculas.stream()
                .filter(p -> p.getNombrePelicula().equals(nombre))
                .findAny()
                .orElse(null);
    }

    public List<Pelicula> obtenerPeliculasPorGenero(TipoGenero genero) {
        return peliculas.stream()
                .filter(p -> p.getGeneroID().equals(genero))
                .collect(Collectors.toList());
    }

    public void registrarPelicula(PeliculaDTO peliculaDTO) throws CinemaException {
        Pelicula pelicula = peliculas.stream()
                .filter(p -> p.getNombrePelicula().equals(peliculaDTO.getNombrePelicula()))
                .findAny()
                .orElse(null);

        if(!Objects.isNull(pelicula)) {
            throw  new CinemaException(PELICULA_YA_EXISTENTE.getMessage(), PELICULA_YA_EXISTENTE.getCode());
        }
        Pelicula nuevaPelicula = peliculaMapper.toPeliculaModel(peliculaDTO);
        nuevaPelicula.setPeliculaID(id.incrementAndGet());
        peliculas.add(nuevaPelicula);
    }

    public List<String> obtenerListaActores(){
        return actores;
    }

}