package model.business.pelicula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.business.negocio.CondicionesDescuento;
import model.constants.TipoGenero;
import model.constants.TipoProyeccion;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {
    private int peliculaID;
    private TipoGenero generoID;
    private String nombrePelicula;
    private int duracionEnMinutos;
    private String director;
    private List<String> actores;
    private TipoProyeccion tipo;
    private CondicionesDescuento condicionesDescuento;

    public int getPeliculaID() {
        // TODO implement here
        return 0;
    }
}