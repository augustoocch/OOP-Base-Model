package model.utils;

import model.business.cine.Sala;
import model.business.cine.Sucursal;
import model.business.negocio.CondicionesDescuento;
import model.constants.TipoTarjeta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static model.constants.NegocioConstantes.*;

public class NegocioUtils {
    public static List<Sala> getSalasCine1(){
        return List.of(sala1, sala2, sala3, sala4, sala5);
    }

    public static List<Sala> getSalasCine2(){
        return List.of(sala6, sala7, sala8, sala9, sala10, sala11);
    }

    public static List<Sala> getTodasLasSalas(){
        return List.of(sala1, sala2, sala3, sala4, sala5, sala6, sala7, sala8, sala9, sala10, sala11);
    }

    public static List<Sucursal> getSucursalesList() {
        return List.of(sucursal1, sucursal2);
    }

    public static List<String> getListaActores(){
        return actoresArgentinos;
    }

    public static List<CondicionesDescuento> getCondicionesDescuento() {
        List<CondicionesDescuento> listado = new ArrayList<>();
        List<Date> fechas = getFechas();
        List<String> idDescuento = getIdDescuento();
        for(int i = 0; i < 10; i++){
            CondicionesDescuento condicionesDescuento = new CondicionesDescuento();
            condicionesDescuento.setIdDescuento(idDescuento.get(i));
            condicionesDescuento.setFchDesde(fechas.get(i));
            condicionesDescuento.setFchHasta(fechas.get(i + 1));
            condicionesDescuento.setDiaSemana(i);
            condicionesDescuento.setPorcentaje(i);
            condicionesDescuento.setTipoTarjeta(TipoTarjeta.values()[i % 5]);
            listado.add(condicionesDescuento);
        }
        return listado;
    }

    public static CondicionesDescuento findCondicionesDescuentoById(String idDescuento){
        return getCondicionesDescuento()
                .stream()
                .filter(condicionesDescuento ->
                        condicionesDescuento.getIdDescuento().equals(idDescuento))
                .findFirst().orElse(null);
    }

    public static List<String>  getListadoHorasCine(){
        List<String> horas = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            horas.add(String.valueOf(i + 10) + ":00");
        }
        return horas;
    }

    private static List<Date> getFechas(){
        List<Date> fechas = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            cal.set(Calendar.MONTH, i);
            fechas.add(cal.getTime());
        }
        return fechas;
    }

    private static List<String> getIdDescuento(){
        List<String> idDescuento = new ArrayList<>();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 11; i++) {
            idDescuento.add("Tipo " + String.valueOf(letras.charAt(i)));
        }
        return idDescuento;
    }
}
