package model.constants;

import lombok.Getter;

@Getter
public enum TipoTarjeta {
    LaNacion("LaNacion", 10),
    Clarin365("LaNacion", 12),
    MovieClub("LaNacion", 15),
    PAMI("LaNacion", 5),
    UADE("LaNacion", 20);

    private final String nombre;
    private final int descuento;

    TipoTarjeta(String nombre, int descuento) {
        this.nombre = nombre;
        this.descuento = descuento;
    }
}