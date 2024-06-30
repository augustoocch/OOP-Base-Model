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
    private int asientosDisponibles;

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
}