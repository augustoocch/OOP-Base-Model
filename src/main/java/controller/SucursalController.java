package controller;

import config.CineConfig;
import lombok.Getter;
import lombok.Setter;
import model.business.cine.Sala;
import model.business.cine.Sucursal;

import java.util.*;

@Getter
@Setter
public class SucursalController {
	private List<Sucursal> sucursalesListado;
    public static SucursalController instancia;
    private CineConfig config = CineConfig.getInstance();
		
    private SucursalController() {
        sucursalesListado = config.getSucursalesList();
    }

    public static SucursalController obtenerInstancia() {
        if(instancia == null){
            instancia = new SucursalController();
        }
        return instancia;
    }

    public List<Sala> obtenerSalas() {
        List<Sala> salas = new ArrayList<>();
        for (Sucursal sucursal : sucursalesListado) {
            salas.addAll(sucursal.getSalas());
        }
        return salas;
    }

    public Sala obtenerSalaPorDenominacion(String salaID) {
        return sucursalesListado.stream()
                .flatMap(s -> s.getSalas().stream())
                .filter(s -> s.getDenominacion().equals(salaID))
                .findAny()
                .orElse(null);
    }
}