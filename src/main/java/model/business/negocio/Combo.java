package model.business.negocio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Combo {
    private int comboID;
    private String descripcion;
    private float precio;
    public CondicionesDescuento Contiene;
}