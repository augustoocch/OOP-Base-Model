package config;

import model.business.cine.Sucursal;
import java.util.ArrayList;
import java.util.List;

public class CineConfig {
    private static CineConfig instance;
    private final float PRECIO_ENTRADA = 200.0f;

    private CineConfig() {}

    public static CineConfig getInstance() {
        if (instance == null) {
            instance = new CineConfig();
        }
        return instance;
    }

    public List<Sucursal> getSucursalesList() {
        Sucursal sucursal1 = new Sucursal(1, "Cine 1", "Direccion 1");
        Sucursal sucursal2 = new Sucursal(2, "Cine 2", "Direccion 2");
        return List.of(sucursal1, sucursal2);
    }

    public List<String> getListaActores(){
        return List.of("Ricardo Darin", "Guillermo Francella",
                "Luis Brandoni", "Julieta Diaz", "Natalia Oreiro", "Gastón Pauls", "China Zorrilla", "Norma Aleandro");
    }

    public List<String> getListadoHorasCine(){
        List<String> horas = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            horas.add(String.valueOf(i + 10) + ":00");
        }
        return horas;
    }

    public float getPrecioEntrada(){
        return PRECIO_ENTRADA;
    }
}
