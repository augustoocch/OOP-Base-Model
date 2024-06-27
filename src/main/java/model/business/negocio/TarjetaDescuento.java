package model.business.negocio;

import lombok.AllArgsConstructor;
import model.constants.TipoTarjeta;

@AllArgsConstructor
public class TarjetaDescuento {
    private int tarjetaID;
    private TipoTarjeta tipoTarjeta;
    private String numeroTarjeta;

    public TipoTarjeta getTipoTarjeta() {
        // TODO implement here
        return null;
    }
}