package controller;

import lombok.Getter;
import lombok.Setter;
import model.business.negocio.Venta;
import model.business.cine.Funcion;
import model.constants.TipoGenero;
import model.constants.TipoTarjeta;
import model.dto.VentaDto;
import model.exception.CinemaException;

import java.util.*;

import static model.exception.ErrorCode.FUNCIONES_NO_ENCONTRADAS;

@Getter
@Setter
public class VentasController {

    private List<Venta> ventas;
    public static VentasController instancia;
    private FuncionController funcionController =  FuncionController.obtenerInstancia();

	
    private VentasController(){
        ventas = new ArrayList<Venta>();
    }

    public static VentasController getInstancia() {
        if(instancia == null){
            instancia = new VentasController();
        }
        return instancia;
    }

    public float recaudacionPorFuncion(int funcionID) {
        // TODO implement here
        return 0.0f;
    }

    /**
     * Caso de secuencia a desarrollar
     * @param peliculaID
     * @return
     */
    public float recaudacionPorPelicula(int peliculaID) {
        List<Funcion> funciones = funcionController.buscarPeliculaPorFuncion(peliculaID);
        if(funciones.isEmpty()){
            return 0;
        }
        float totalrecuadado = 0.0f;
        for (Funcion funcion:funciones) {
            Venta venta = buscarVentaPorFuncion(funcion);
            if(Objects.isNull(venta)){
                totalrecuadado=+venta.calcularMontoDeLaVentaPorFuncionCombos();
            }
        }
    	return totalrecuadado;
    }

    public float recaudacionPorTarjetaDescuento(TipoTarjeta tipoTarjeta) {
        // TODO implement here
        return 0.0f;
    }

    public void comboMasVendido() {
        // TODO implement here
    }

    private  Venta buscarVentaPorFuncion(Funcion funcion){
        for (Venta venta:getVentas()) {
            if(Objects.equals(funcion,venta.getFuncion())){
                return venta;
            }
        }
        return null;
    }

    /**
     * View a desarrollar
     *
     * @param genero
     * @return
     */
    public List<VentaDto> funcionesVendidasPorGenero(TipoGenero genero) throws CinemaException {
        List<VentaDto> ventaDtos = new ArrayList<>();
        List<Funcion> funciones = funcionController.buscarPeliculaPorGenerosFuncion(genero);
        if(funciones.isEmpty()){
            throw new CinemaException(FUNCIONES_NO_ENCONTRADAS.getMessage(), FUNCIONES_NO_ENCONTRADAS.getCode());
        }
        for (Funcion funcion:funciones) {
            Venta venta = buscarVentaPorFuncion(funcion);
            if(Objects.isNull(venta)){
                ventaDtos.add(modelVentaToDto(venta));
            }
        }
        return ventaDtos;
    }

    public VentaDto modelVentaToDto(Venta venta){
        return modelVentaToDto(venta);
    }
}