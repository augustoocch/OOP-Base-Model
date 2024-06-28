package model.mapper;

import model.business.cine.Funcion;
import model.business.negocio.Venta;
import model.dto.FuncionDTO;
import model.dto.VentaDTO;

import static model.mapper.FuncionMapper.toModelFuncion;

public class VentaMapper {

    public static VentaDTO toVentaDto(FuncionDTO funcionDTO) {
        VentaDTO dto = new VentaDTO();
        return dto;
    }

    public static Venta toVentaModel(VentaDTO dto) {
        Funcion funcion = toModelFuncion(dto.getFuncionDTO());
        Venta model = new Venta();
        model.setCombos(dto.getCombos());
        model.setFuncion(funcion);
        model.setFchVenta(dto.getFchVenta());
        model.setTarjetaDescuento(dto.getTarjetaDescuento());
        model.setAsientos(dto.getAsientos());
        return model;
    }
}
