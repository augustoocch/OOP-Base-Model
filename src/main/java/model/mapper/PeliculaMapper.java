package model.mapper;

import config.CineConfig;
import model.business.negocio.CondicionesDescuento;
import model.business.pelicula.Pelicula;
import model.dto.PeliculaDTO;

public class PeliculaMapper {
    private CineConfig config = CineConfig.getInstance();

    public PeliculaMapper() {}

    public PeliculaDTO toPeliculaDto(Pelicula model) {
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

    public Pelicula toPeliculaModel(PeliculaDTO dto) {
        Pelicula model = new Pelicula();
        model.setGeneroID(dto.getGeneroID());
        model.setNombrePelicula(dto.getNombrePelicula());
        model.setDuracionEnMinutos(dto.getDuracionEnMinutos());
        model.setDirector(dto.getDirector());
        model.setActores(dto.getActores());
        model.setTipo(dto.getTipo());
        CondicionesDescuento condicionesDescuento = config.findCondicionesDescuentoById(dto.getCondicionesDescuento());
        model.setCondicionesDescuento(condicionesDescuento);
        return model;
    }
}
