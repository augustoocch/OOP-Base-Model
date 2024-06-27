package controller;

import lombok.Getter;
import lombok.Setter;
import model.business.cine.Sala;
import model.business.cine.Sucursal;

import java.util.*;
import static model.utils.NegocioUtils.getSucursalesList;

@Getter
@Setter
public class SucursalController {

	private List<Sucursal> sucursalesListado;
    public static SucursalController instancia;
		
    private SucursalController() {
        sucursalesListado = getSucursalesList();
    }

    public static SucursalController obtenerInstancia() {
        if(instancia == null){
            instancia = new SucursalController();
        }
        return instancia;
    }

    public Sala obtenerSalaPorDenominacion(String salaID) {
        return sucursalesListado.stream()
                .flatMap(s -> s.getSalas().stream())
                .filter(s -> s.getDenominacion().equals(salaID))
                .findAny()
                .orElse(null);
    }
}