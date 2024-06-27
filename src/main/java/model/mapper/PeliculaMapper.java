package model.mapper;

import model.business.negocio.CondicionesDescuento;
import model.business.pelicula.Pelicula;
import model.dto.PeliculaDTO;

import static model.utils.NegocioUtils.findCondicionesDescuentoById;

public class PeliculaMapper {

    public static PeliculaDTO toPeliculaDto(Pelicula model) {
        PeliculaDTO dto = new PeliculaDTO();
        dto.setGeneroID(model.getGeneroID());
        dto.setNombrePelicula(model.getNombrePelicula());
        dto.setDuracionEnMinutos(model.getDuracionEnMinutos());
        dto.setDirector(model.getDirector());
        dto.setActores(model.getActores());
        dto.setTipo(model.getTipo());
        dto.setCondicionesDescuento(model.getCondicionesDescuento().getIdDescuento());
        return dto;
    }

    public static Pelicula toPeliculaModel(PeliculaDTO dto) {
        Pelicula model = new Pelicula();
        model.setGeneroID(dto.getGeneroID());
        model.setNombrePelicula(dto.getNombrePelicula());
        model.setDuracionEnMinutos(dto.getDuracionEnMinutos());
        model.setDirector(dto.getDirector());
        model.setActores(dto.getActores());
        model.setTipo(dto.getTipo());
        CondicionesDescuento condicionesDescuento = findCondicionesDescuentoById(dto.getCondicionesDescuento());
        model.setCondicionesDescuento(condicionesDescuento);
        return model;
    }
}
