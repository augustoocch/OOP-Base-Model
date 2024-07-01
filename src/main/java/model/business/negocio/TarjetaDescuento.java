package model.business.negocio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.constants.TipoTarjeta;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TarjetaDescuento {
    private int tarjetaID;
    private TipoTarjeta tipoTarjeta;
    private String numeroTarjeta;
}