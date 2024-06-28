package model.business.negocio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.business.cine.Funcion;
import model.constants.TipoTarjeta;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    private int ventaID;
    private Integer asientos;
    private Date fchVenta;
    private List<Combo> combos;
    private Funcion funcion;
    private TarjetaDescuento tarjetaDescuento;

    public int getFuncionID() {
        // TODO implement here
        return 0;
    }

    public float getTotal() {
        // TODO implement here
        return 0.0f;
    }

    public int getPeliculaID() {
        // TODO implement here
        return 0;
    }

    public TipoTarjeta getTipoTarjeta() {
        // TODO implement here
        return null;
    }

    public List<Combo> getListaComboID() {
		return combos;
		
        // TODO implement here
    }

    public float calcularMontoPorComboDeVenta(){
        float total=  0.0f;
        for (Combo combo:getListaComboID()) {
           total =+  (combo.getPrecio()-(combo.getPrecio()*
                   CondicionesDescuento.getDescuentoPorTarjeta(tarjetaDescuento.getTipoTarjeta())));
        }
        return total;
    }


    public float calcularMontoDeLaVentaPorFuncionCombos(){
        return funcion
                .calcularMontoPorEntradaDeLaPelicula()+calcularMontoPorComboDeVenta();
    }
}