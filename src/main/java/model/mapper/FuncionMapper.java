package model.mapper;

import model.business.cine.Funcion;
import model.dto.FuncionDTO;

public class FuncionMapper {

    public static Funcion toModelFuncion(FuncionDTO funcionDTO) {
        Funcion funcion = new Funcion();
        funcion.setFecha(funcionDTO.getFecha());
        funcion.setPelicula(funcionDTO.getPelicula());
        funcion.setSala(funcionDTO.getSala());
        funcion.setHorario(funcionDTO.getHorario());
        return funcion;
    }
}
