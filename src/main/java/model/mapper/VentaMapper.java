package model.mapper;

import model.business.negocio.Venta;
import model.dto.FuncionDTO;
import model.dto.VentaDto;

public class VentaMapper {

    public static VentaDto toDto(FuncionDTO funcionDTO) {
        VentaDto dto = new VentaDto();
        return dto;
    }

    public static Venta toModel(VentaDto dto) {
        Venta model = new Venta();
        model.setCombos(dto.getCombos());
        model.setFuncion(dto.getFuncion());
        model.setFchVenta(dto.getFchVenta());
        model.setTarjetaDescuento(dto.getTarjetaDescuento());
        return model;
    }
}
