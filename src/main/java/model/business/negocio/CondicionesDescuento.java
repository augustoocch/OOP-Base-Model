package model.business.negocio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.constants.TipoTarjeta;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class CondicionesDescuento {

    public float getDescuento(TipoTarjeta tarjeta){
        float descuento = getDescuentoPorTarjeta(tarjeta);
        if (LocalDate.now().getDayOfWeek().getValue() <= 3){
            descuento += 0.5f;
        }
        return descuento;
    }

    public static float getDescuentoPorTarjeta(TipoTarjeta tipoTarjeta){
        switch (tipoTarjeta){
            case PAMI -> { return 0.12f; }
            case UADE,MovieClub ->{ return 0.15f;}
            case LaNacion,Clarin365 -> {return 0.18f;}
            default -> {return 0.0f;}
        }
    }
}