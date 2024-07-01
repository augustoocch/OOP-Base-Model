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
        funcion.setFuncionID(funcionDTO.getFuncionID());
        funcion.setEntradas(funcionDTO.getEntradas());
        funcion.setFuncionID(funcionDTO.getFuncionID());
        funcion.setSala(funcionDTO.getSala());
        funcion.setAsientosDisponibles(funcionDTO.getAsientosDisponibles());
        return funcion;
    }

    public FuncionDTO toDTOFuncion(Funcion funcion) {
        FuncionDTO funcionDTO = new FuncionDTO();
        funcionDTO.setEntradas(funcion.getEntradas());
        funcionDTO.setFuncionID(funcion.getFuncionID());
        funcionDTO.setFecha(funcion.getFecha());
        funcionDTO.setPelicula(funcion.getPelicula());
        funcionDTO.setSala(funcion.getSala());
        funcionDTO.setHorario(funcion.getHorario());
        funcionDTO.setAsientosDisponibles(funcion.getAsientosDisponibles());
        return funcionDTO;
    }
}
