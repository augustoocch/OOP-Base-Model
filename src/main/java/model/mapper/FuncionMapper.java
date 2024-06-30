package model.mapper;

import model.business.cine.Funcion;
import model.dto.FuncionDTO;

public class FuncionMapper {

    public FuncionMapper() {}

    public Funcion toModelFuncion(FuncionDTO funcionDTO) {
        Funcion funcion = new Funcion();
        funcion.setFecha(funcionDTO.getFecha());
        funcion.setPelicula(funcionDTO.getPelicula());
        funcion.setSala(funcionDTO.getSala());
        funcion.setHorario(funcionDTO.getHorario());
        return funcion;
    }

    public FuncionDTO toDTOFuncion(Funcion funcion) {
        FuncionDTO funcionDTO = new FuncionDTO();
        funcionDTO.setFecha(funcion.getFecha());
        funcionDTO.setPelicula(funcion.getPelicula());
        funcionDTO.setSala(funcion.getSala());
        funcionDTO.setHorario(funcion.getHorario());
        return funcionDTO;
    }
}
