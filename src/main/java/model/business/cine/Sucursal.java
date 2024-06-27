package model.business.cine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Sucursal {
    private int sucursalID;
    private String denominacion;
    private String direccion;
    private List<Sala> salas;
}