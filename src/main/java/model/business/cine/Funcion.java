package model.business.cine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.business.pelicula.Entrada;
import model.business.pelicula.Pelicula;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Funcion {
    private Pelicula pelicula;
    private int funcionID;
    private String horario;
    private Date fecha;
    private List<Entrada> entradas;
    private Sala sala;

    public Funcion() {}


    public int getSalaID() {
        // TODO implement here
        return 0;
    }

    public int getPeliculaID() {
        // TODO implement here
        if(Objects.nonNull(getPelicula().getPeliculaID())){

        }
        return 0;
    }

    public int getCantidadAsientosDisponibles() {
    	
		return 0;
        // TODO implement here
    }

    public float calcularMontoPorEntradaDeLaPelicula(){
        float total = 0.0f;
        for (Entrada entrada:getEntradas()) {
            total = total+ (entrada.getPrecio() -
                    (entrada.getPrecio()*pelicula.getCondicionesDescuento().getDescuento()));
        }
        return total;
    }
}