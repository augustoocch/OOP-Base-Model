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
public class VentaDTO {
    private FuncionDTO funcionDTO;
    private Integer asientos;
    private List<Integer> asientosSeleccionados;
    private Date fchVenta;
    private List<Combo> combos;
    private TarjetaDescuento tarjetaDescuento;

}
