package controller;

import model.business.negocio.CondicionesDescuento;
import model.constants.TipoTarjeta;

import java.util.*;

public class DescuentoController {
	private static DescuentoController instancia;
	private CondicionesDescuento condicionesDescuento;
	
    private DescuentoController() {
    	condicionesDescuento = new CondicionesDescuento();
    }

	public static DescuentoController obtenerInstancia() {
		if(instancia == null){
			instancia = new DescuentoController();
		}
		return instancia;
	}

	public float obtenerPorcentajeDescuento(TipoTarjeta tarjeta) {
		return condicionesDescuento.getDescuento(tarjeta);
	}

	public List<String> obtenerTarjetas() {
		TipoTarjeta[] tarjetas = TipoTarjeta.values();
		List<String> tarjetasString = new ArrayList<>();
		for (TipoTarjeta tarjeta : tarjetas) {
			if(tarjeta != TipoTarjeta.SIN_DESCUENTO){
				tarjetasString.add(tarjeta.name());
			}
		}
		return tarjetasString;
	}
}