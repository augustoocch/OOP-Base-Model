package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.business.cine.Sala;
import model.business.pelicula.Entrada;
import model.business.pelicula.Pelicula;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionDTO {
    private Pelicula pelicula;
    private String horario;
    private Date fecha;
    private Sala sala;
    private int funcionID;
    private List<Entrada> entradas;
    private int asientosDisponibles;
}
