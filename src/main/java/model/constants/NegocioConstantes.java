package model.constants;

import model.business.cine.Sala;
import model.business.cine.Sucursal;
import java.util.List;

import static model.utils.NegocioUtils.getSalasCine1;
import static model.utils.NegocioUtils.getSalasCine2;

public class NegocioConstantes {
    public static Sala sala1 = new Sala(1, "Sala 1", 100);
    public static Sala sala2 = new Sala(2, "Sala 2", 110);
    public static Sala sala3 = new Sala(3, "Sala 3", 200);
    public static Sala sala4 = new Sala(4, "Sala 4", 200);
    public static Sala sala5 = new Sala(5, "Sala 5", 210);

    public static Sala sala6 = new Sala(6, "Sala 6", 100);
    public static Sala sala7 = new Sala(7, "Sala 7", 150);
    public static Sala sala8 = new Sala(8, "Sala 8", 250);
    public static Sala sala9 = new Sala(9, "Sala 9", 250);
    public static Sala sala10 = new Sala(10, "Sala 10", 210);
    public static Sala sala11 = new Sala(11, "Sala 11", 200);

    public static Sucursal sucursal1 = new Sucursal(1, "Cine 1", "Direccion 1", getSalasCine1());
    public static Sucursal sucursal2 = new Sucursal(2, "Cine 2", "Direccion 2", getSalasCine2());

    public static float PRECIO_ENTRADA = 200.0f;

    public static List<String> actoresArgentinos = List.of("Ricardo Darin", "Guillermo Francella",
            "Luis Brandoni", "Julieta Diaz", "Natalia Oreiro", "Gastón Pauls", "China Zorrilla", "Norma Aleandro");

}
