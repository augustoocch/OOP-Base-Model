package controller;

import lombok.Getter;
import lombok.Setter;
import model.business.pelicula.Pelicula;
import model.constants.TipoGenero;
import model.dto.PeliculaDTO;
import model.exception.CinemaException;

import java.util.*;
import java.util.stream.Collectors;

import static model.exception.ErrorCode.PELICULA_YA_EXISTENTE;
import static model.mapper.PeliculaMapper.toPeliculaModel;

@Getter
@Setter
public class PeliculasController {
	
	private List<Pelicula> peliculas;
    public static PeliculasController instancia;
	
    public PeliculasController() {
    	peliculas= new ArrayList<Pelicula>();
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
        Pelicula nuevaPelicula = toPeliculaModel(peliculaDTO);
        peliculas.add(nuevaPelicula);
    }

    public void ABM() {
        // TODO implement here
    }

}