package model.mapper;

import model.business.cine.Funcion;
import model.business.negocio.Venta;
import model.dto.FuncionDTO;
import model.dto.VentaDTO;

import java.util.Objects;

public class VentaMapper {
    private FuncionMapper funcionMapper;

    public VentaMapper() {
        funcionMapper = new FuncionMapper();
    }

    public Venta toVentaModel(VentaDTO dto) {
        Funcion funcion = funcionMapper.toModelFuncion(dto.getFuncionDTO());
        Venta model = new Venta();
        model.setCombos(dto.getCombos());
        model.setFuncion(funcion);
        model.setFchVenta(dto.getFchVenta());
        model.setTarjetaDescuento(Objects.nonNull(dto.getTarjetaDescuento())
                ? dto.getTarjetaDescuento()
                : null);
        model.setAsientos(dto.getAsientos());
        return model;
    }
}
