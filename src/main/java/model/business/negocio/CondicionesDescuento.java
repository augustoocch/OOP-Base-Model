package model.business.negocio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.constants.TipoTarjeta;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CondicionesDescuento {
    private String idDescuento;
    private Date fchDesde;
    private Date fchHasta;
    private int diaSemana;
    private float porcentaje;
    private TipoTarjeta tipoTarjeta;
    private List<TarjetaDescuento> TarjetaDescuento;

    public float getDescuento(){
        float descuento = 0.0f;
        for (TarjetaDescuento tarjetaDescuento: getTarjetaDescuento()) {
            descuento += getDescuentoPorTarjeta(tarjetaDescuento.getTipoTarjeta());
        }
        descuento=descuento+porcentaje;
        return descuento;
    }

    public static float getDescuentoPorTarjeta(TipoTarjeta tipoTarjeta){
        switch (tipoTarjeta){
            case PAMI -> { return 0.25f; }
            case UADE,MovieClub ->{ return 0.15f;}
            case LaNacion,Clarin365 -> {return 0.5f;}
            default -> {return 0.0f;}
        }
    }
}