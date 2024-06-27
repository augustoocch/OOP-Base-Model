package controller;

import model.business.negocio.CondicionesDescuento;

import java.util.*;

import static model.utils.NegocioUtils.getCondicionesDescuento;

public class DescuentoController {
	private static DescuentoController instancia;
	private List<CondicionesDescuento> descuentos;
	
    private DescuentoController() {
    	descuentos = getCondicionesDescuento();
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