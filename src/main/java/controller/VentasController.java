package controller;

import config.CineConfig;
import lombok.Getter;
import lombok.Setter;
import model.business.negocio.Venta;
import model.business.cine.Funcion;
import model.business.pelicula.Entrada;
import model.constants.TipoGenero;
import model.constants.TipoTarjeta;
import model.dto.VentaDTO;
import model.exception.CinemaException;
import model.mapper.PeliculaMapper;
import model.mapper.VentaMapper;

import java.util.*;
import static model.exception.ErrorCode.*;

@Getter
@Setter
public class VentasController {

    private List<Venta> ventas;
    private static VentasController instancia;
    private FuncionController funcionController;
    private CineConfig config;
    private VentaMapper ventaMapper;

	
    private VentasController(){
        funcionController = FuncionController.obtenerInstancia();
        ventas = new ArrayList<Venta>();
        config = CineConfig.getInstance();
        ventaMapper = new VentaMapper();
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
            if (funcion.getPelicula().getPeliculaID() == peliculaID){
                List<Entrada> e =funcion.getEntradas();
                if(Objects.nonNull(e)){
                    for (Entrada entrada:e) {
                        totalrecuadado = totalrecuadado + entrada.getPrecio();
                    }
                }
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
        if(Objects.isNull(funcion.getEntradas()) || funcion.getEntradas().isEmpty()){
            funcion.setEntradas(new ArrayList<>());
            for(Integer e : venta.getAsientosSeleccionados()){
                Entrada entrada = new Entrada();
                entrada.setNroAsiento(e);
                entrada.setPrecio(config.getPrecioEntrada());
                funcion.getEntradas().add(entrada);
            }
            return;
        }
        checkAsientoYaSeleccionado(funcion,venta);

        for(Integer e : venta.getAsientosSeleccionados()){
            Entrada entrada = new Entrada();
            entrada.setNroAsiento(e);
            entrada.setPrecio(config.getPrecioEntrada());
            funcion.getEntradas().add(entrada);
        }
    }

    private void checkAsientoYaSeleccionado(Funcion funcion, VentaDTO venta) throws CinemaException {
        for (int i = 0; i < venta.getAsientos(); i++) {
            Entrada e = funcion.getEntradas().get(i);
            Integer  asiento = venta.getAsientosSeleccionados().get(i);
            if(e.getNroAsiento() == asiento) {
                throw new CinemaException(ASIENTO_YA_OCUPADO.getMessage(), ASIENTO_YA_OCUPADO.getCode());
            }
        }
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