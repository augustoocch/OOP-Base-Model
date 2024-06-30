package controller;

import config.CineConfig;
import model.business.negocio.CondicionesDescuento;

import java.util.*;

public class DescuentoController {
	private static DescuentoController instancia;
	private List<CondicionesDescuento> descuentos;
	private CineConfig config = CineConfig.getInstance();
	
    private DescuentoController() {
    	descuentos = config.getCondicionesDescuento();
    }

	public static DescuentoController obtenerInstancia() {
		if(instancia == null){
			instancia = new DescuentoController();
		}
		return instancia;
	}

    public void ABM() {
	}
}