package model.business.cine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Sala {
    private int salaID;
    private String denominacion;
    private int asientos;
}