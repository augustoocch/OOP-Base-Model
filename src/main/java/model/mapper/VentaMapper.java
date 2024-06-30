package model.mapper;

import model.business.cine.Funcion;
import model.business.negocio.Venta;
import model.dto.FuncionDTO;
import model.dto.VentaDTO;

public class VentaMapper {
    private FuncionMapper funcionMapper;

    public VentaMapper() {
        funcionMapper = new FuncionMapper();
    }

    public VentaDTO toVentaDto(FuncionDTO funcionDTO) {
        VentaDTO dto = new VentaDTO();
        return dto;
    }

    public Venta toVentaModel(VentaDTO dto) {
        Funcion funcion = funcionMapper.toModelFuncion(dto.getFuncionDTO());
        Venta model = new Venta();
        model.setCombos(dto.getCombos());
        model.setFuncion(funcion);
        model.setFchVenta(dto.getFchVenta());
        model.setTarjetaDescuento(dto.getTarjetaDescuento());
        model.setAsientos(dto.getAsientos());
        return model;
    }
}
