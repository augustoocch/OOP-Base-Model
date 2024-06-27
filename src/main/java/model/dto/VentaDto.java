package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.business.cine.Funcion;
import model.business.negocio.Combo;
import model.business.negocio.TarjetaDescuento;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaDto {
    private FuncionDTO funcionDTO;
    private Date fchVenta;
    private List<Combo> combos;
    private Funcion funcion;
    private TarjetaDescuento tarjetaDescuento;

}
