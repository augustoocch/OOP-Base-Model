package controller;

import lombok.Getter;
import lombok.Setter;
import model.business.negocio.Venta;
import model.business.cine.Funcion;
import model.constants.TipoGenero;
import model.constants.TipoTarjeta;
import model.dto.VentaDTO;
import model.exception.CinemaException;

import java.util.*;

import static model.exception.ErrorCode.FUNCIONES_NO_ENCONTRADAS;
import static model.exception.ErrorCode.NO_HAY_ASIENTOS;
import static model.mapper.VentaMapper.toVentaModel;

@Getter
@Setter
public class VentasController {

    private List<Venta> ventas;
    public static VentasController instancia;
    private FuncionController funcionController =  FuncionController.obtenerInstancia();

	
    private VentasController(){
        ventas = new ArrayList<Venta>();
    }

    public static VentasController obtenerInstancia() {
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
        List<Funcion> funciones = funcionController.buscarPeliculaPorIdPelicula(peliculaID);
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

    public List<VentaDTO> funcionesVendidasPorGenero(TipoGenero genero) throws CinemaException {
        List<VentaDTO> ventaDtos = new ArrayList<>();
        List<Funcion> funciones = funcionController.buscarFuncionPorGeneroPelicula(genero);
        if(funciones.isEmpty()){
            throw new CinemaException(FUNCIONES_NO_ENCONTRADAS.getMessage(), FUNCIONES_NO_ENCONTRADAS.getCode());
        }
        for (Funcion funcion:funciones) {
            Venta venta = buscarVentaPorFuncion(funcion);
            if(!Objects.isNull(venta)){
                ventaDtos.add(modelVentaToDto(venta));
            }
        }
        return ventaDtos;
    }

    public void registrarVenta(VentaDTO venta) throws CinemaException {
        if(Objects.isNull(venta)){
            return;
        }
        venta.getFuncionDTO().getFecha();
        Funcion funcion = funcionController.buscarFuncionPorFechaYPelicula(
                venta.getFuncionDTO().getFecha(),
                venta.getFuncionDTO().getPelicula().getNombrePelicula()
        );
        if(funcion.getAsientosDisponibles() < venta.getAsientos()){
            throw new CinemaException(NO_HAY_ASIENTOS.getMessage(), NO_HAY_ASIENTOS.getCode());
        }
        funcion.setAsientosDisponibles(funcion.getAsientosDisponibles() - venta.getAsientos());
        Venta ventaModel = toVentaModel(venta);
        ventas.add(ventaModel);
    }

    private VentaDTO modelVentaToDto(Venta venta){
        return modelVentaToDto(venta);
    }

    private  Venta buscarVentaPorFuncion(Funcion funcion){
        for (Venta venta:getVentas()) {
            if(Objects.equals(funcion,venta.getFuncion())){
                return venta;
            }
        }
        return null;
    }
}