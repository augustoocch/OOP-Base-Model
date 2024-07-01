package model.business.cine;

import config.CineConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class Sucursal {
    private int sucursalID;
    private String denominacion;
    private String direccion;
    private List<Sala> salas;

    public Sucursal(int sucursalID, String denominacion, String direccion) {
        this.sucursalID = sucursalID;
        this.denominacion = denominacion;
        this.direccion = direccion;
        if(sucursalID == 1){
            salas = getSalasCine1();
        }else {
            salas = getSalasCine2();
        }
    }

    private List<Sala> getSalasCine1(){
        Sala sala1 = new Sala(1, "Sala 1", 100);
        Sala sala2 = new Sala(2, "Sala 2", 110);
        Sala sala3 = new Sala(3, "Sala 3", 200);
        Sala sala4 = new Sala(4, "Sala 4", 200);
        Sala sala5 = new Sala(5, "Sala 5", 210);
        return List.of(sala1, sala2, sala3, sala4, sala5);
    }

    private List<Sala> getSalasCine2(){
        Sala sala6 = new Sala(6, "Sala 6", 100);
        Sala sala7 = new Sala(7, "Sala 7", 150);
        Sala sala8 = new Sala(8, "Sala 8", 250);
        Sala sala9 = new Sala(9, "Sala 9", 250);
        Sala sala10 = new Sala(10, "Sala 10", 210);
        Sala sala11 = new Sala(11, "Sala 11", 200);
        return List.of(sala6, sala7, sala8, sala9, sala10, sala11);
    }
}