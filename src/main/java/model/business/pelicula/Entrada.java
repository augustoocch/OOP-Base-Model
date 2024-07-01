package model.business.pelicula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.business.cine.Funcion;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entrada {
    private int id;
    private int nroAsiento;
    private float precio;
}