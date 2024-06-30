package controller;

import config.CineConfig;
import model.business.negocio.CondicionesDescuento;
import model.constants.TipoTarjeta;

import java.util.*;

public class DescuentoController {
	private static DescuentoController instancia;
	private List<CondicionesDescuento> descuentos;
	private CineConfig config;
	
    private DescuentoController() {
		config = CineConfig.getInstance();
    	descuentos = config.getCondicionesDescuento();
    }

	public static DescuentoController obtenerInstancia() {
		if(instancia == null){
			instancia = new DescuentoController();
		}
		return instancia;
	}

	public List<CondicionesDescuento> obtenerDescuentos() {
		return descuentos;
	}

	public float obtenerPorcentajeDescuento(TipoTarjeta tarjeta) {
		CondicionesDescuento condicionesDescuento = new CondicionesDescuento();
		return condicionesDescuento.getDescuento(tarjeta);
	}

	public List<String> obtenerTarjetas() {
		TipoTarjeta[] tarjetas = TipoTarjeta.values();
		List<String> tarjetasString = new ArrayList<>();
		for (TipoTarjeta tarjeta : tarjetas) {
			tarjetasString.add(tarjeta.name());
		}
		return tarjetasString;
	}
}