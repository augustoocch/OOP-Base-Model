package controller;

import config.CineConfig;
import lombok.Getter;
import lombok.Setter;
import model.business.negocio.Venta;
import model.business.cine.Funcion;
import model.business.pelicula.Entrada;
import model.dto.FuncionDTO;
import model.dto.VentaDTO;
import model.exception.CinemaException;
import model.mapper.FuncionMapper;
import model.mapper.VentaMapper;

import java.util.*;
import static model.exception.ErrorCode.*;

@Getter
@Setter
public class VentaController {
    private List<Venta> ventas;
    private static VentaController instancia;
    private FuncionController funcionController;
    private CineConfig config;
    private VentaMapper ventaMapper;
    private DescuentoController descuentoController;
    private final float PRECIO_ENTRADA;
    private FuncionMapper funcionMapper;
	
    private VentaController(){
        funcionController = FuncionController.obtenerInstancia();
        descuentoController = DescuentoController.obtenerInstancia();
        ventas = new ArrayList<Venta>();
        config = CineConfig.getInstance();
        ventaMapper = new VentaMapper();
        PRECIO_ENTRADA = config.getPrecioEntrada();
        funcionMapper = new FuncionMapper();
    }

    public static VentaController obtenerInstancia() {
        if(instancia == null){
            instancia = new VentaController();
        }
        return instancia;
    }

    public float recaudacionPorPelicula(int peliculaID) {
        List<FuncionDTO> funciones = funcionController.buscarFuncionesPorIdPelicula(peliculaID);
        if(funciones.isEmpty()){
            return 0;
        }
        float totalrecuadado = 0.0f;
        for (FuncionDTO funcion:funciones) {
                List<Entrada> e = funcion.getEntradas();
                if(Objects.nonNull(e)){
                    for (Entrada entrada:e) {
                        totalrecuadado = totalrecuadado + entrada.getPrecio();
                    }
                }
        }
    	return totalrecuadado;
    }

    public void registrarVenta(VentaDTO venta) throws CinemaException {
        if(Objects.isNull(venta)){
            return;
        }
        Funcion funcion = funcionController.buscarFuncionPorFechaYPelicula(
                venta.getFuncionDTO().getFecha(),
                venta.getFuncionDTO().getHorario(),
                venta.getFuncionDTO().getPelicula().getNombrePelicula()
        );
        if(funcion.getAsientosDisponibles() < venta.getAsientos()){
            throw new CinemaException(NO_HAY_ASIENTOS.getMessage(), NO_HAY_ASIENTOS.getCode());
        }
        funcion.setAsientosDisponibles(funcion.getAsientosDisponibles() - venta.getAsientos());
        agregarEntradas(funcion,venta);
        Venta ventaModel = ventaMapper.toVentaModel(venta);
        ventas.add(ventaModel);
    }

    private void agregarEntradas(Funcion funcion, VentaDTO venta) throws CinemaException {
        float descuento = descuentoController.obtenerPorcentajeDescuento(venta.getTarjetaDescuento().getTipoTarjeta());
        float entradaConDescuento = PRECIO_ENTRADA - (PRECIO_ENTRADA * descuento);
        if(Objects.isNull(funcion.getEntradas()) || funcion.getEntradas().isEmpty()){
            funcion.setEntradas(new ArrayList<>());
            for(Integer e : venta.getAsientosSeleccionados()){
                Entrada entrada = new Entrada();
                entrada.setNroAsiento(e);
                entrada.setPrecio(entradaConDescuento);
                funcion.getEntradas().add(entrada);
            }
            return;
        }
        checkAsientoYaSeleccionado(funcion,venta);

        for(Integer e : venta.getAsientosSeleccionados()){
            Entrada entrada = new Entrada();
            entrada.setNroAsiento(e);
            entrada.setPrecio(entradaConDescuento);
            funcion.getEntradas().add(entrada);
        }
    }

    private void checkAsientoYaSeleccionado(Funcion funcion, VentaDTO venta) throws CinemaException {
        for (int i = 0; i < venta.getAsientos(); i++) {
            Integer asiento = venta.getAsientosSeleccionados().get(i);
            for (Entrada e : funcion.getEntradas()) {
                if (e.getNroAsiento() == asiento) {
                    throw new CinemaException(ASIENTO_YA_OCUPADO.getMessage(), ASIENTO_YA_OCUPADO.getCode());
                }
            }
        }
    }

    public static void eliminarInstancia(){
        instancia = null;
    }
}