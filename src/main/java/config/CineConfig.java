package config;

import model.business.cine.Sala;
import model.business.cine.Sucursal;
import model.business.negocio.CondicionesDescuento;
import model.constants.TipoTarjeta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public static List<Sala> getSalasCine1(){
        Sala sala1 = new Sala(1, "Sala 1", 100);
        Sala sala2 = new Sala(2, "Sala 2", 110);
        Sala sala3 = new Sala(3, "Sala 3", 200);
        Sala sala4 = new Sala(4, "Sala 4", 200);
        Sala sala5 = new Sala(5, "Sala 5", 210);
        return List.of(sala1, sala2, sala3, sala4, sala5);
    }

    public List<Sala> getSalasCine2(){
        Sala sala6 = new Sala(6, "Sala 6", 100);
        Sala sala7 = new Sala(7, "Sala 7", 150);
        Sala sala8 = new Sala(8, "Sala 8", 250);
        Sala sala9 = new Sala(9, "Sala 9", 250);
        Sala sala10 = new Sala(10, "Sala 10", 210);
        Sala sala11 = new Sala(11, "Sala 11", 200);
        return List.of(sala6, sala7, sala8, sala9, sala10, sala11);
    }

    public List<Sala> getTodasLasSalas(){
        List<Sala> salasCine1 = getSalasCine1();
        List<Sala> salasCine2 = getSalasCine2();
        List<Sala> todasLasSalas = new ArrayList<>();
        todasLasSalas.addAll(salasCine1);
        todasLasSalas.addAll(salasCine2);
        return todasLasSalas;
    }

    public List<Sucursal> getSucursalesList() {
        Sucursal sucursal1 = new Sucursal(1, "Cine 1", "Direccion 1", getSalasCine1());
        Sucursal sucursal2 = new Sucursal(2, "Cine 2", "Direccion 2", getSalasCine2());
        return List.of(sucursal1, sucursal2);
    }

    public List<String> getListaActores(){
        return List.of("Ricardo Darin", "Guillermo Francella",
                "Luis Brandoni", "Julieta Diaz", "Natalia Oreiro", "Gastón Pauls", "China Zorrilla", "Norma Aleandro");
    }

    public List<CondicionesDescuento> getCondicionesDescuento() {
        List<CondicionesDescuento> listado = new ArrayList<>();
        List<Date> fechas = getFechas();
        List<String> idDescuento = getIdDescuento();
        for(int i = 0; i < 10; i++){
            CondicionesDescuento condicionesDescuento = new CondicionesDescuento();
            condicionesDescuento.setIdDescuento(idDescuento.get(i));
            condicionesDescuento.setPorcentaje(i);
            condicionesDescuento.setTipoTarjeta(TipoTarjeta.values()[i % 5]);
            listado.add(condicionesDescuento);
        }
        return listado;
    }

    public CondicionesDescuento findCondicionesDescuentoById(String idDescuento){
        return getCondicionesDescuento()
                .stream()
                .filter(condicionesDescuento ->
                        condicionesDescuento.getIdDescuento().equals(idDescuento))
                .findFirst().orElse(null);
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

    private List<Date> getFechas(){
        List<Date> fechas = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            cal.set(Calendar.MONTH, i);
            fechas.add(cal.getTime());
        }
        return fechas;
    }

    private List<String> getIdDescuento(){
        List<String> idDescuento = new ArrayList<>();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 11; i++) {
            idDescuento.add("Tipo " + String.valueOf(letras.charAt(i)));
        }
        return idDescuento;
    }
}
