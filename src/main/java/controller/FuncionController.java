package controller;

import config.CineConfig;
import model.business.cine.Funcion;
import model.business.pelicula.Entrada;
import model.constants.TipoGenero;
import model.dto.FuncionDTO;
import model.exception.CinemaException;
import model.mapper.FuncionMapper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import static model.exception.ErrorCode.FUNCIONES_NO_ENCONTRADAS;
import static model.exception.ErrorCode.FUNCION_YA_EXISTENTE;

public class FuncionController {
    private AtomicInteger id;
    private List<Funcion> funciones;
    private static FuncionController instancia;
    private CineConfig config;
    private FuncionMapper funcionMapper;

    private FuncionController() {
        funciones = new ArrayList<Funcion>();
        id = new AtomicInteger(0);
        config = CineConfig.getInstance();
        funcionMapper = new FuncionMapper();
    }

    public static FuncionController obtenerInstancia() {
        if (instancia == null) {
            instancia = new FuncionController();
        }
        return instancia;
    }

    public void ABM() {
        // TODO implement here
    }

    public int obtenerAsientosDisponiblePorFuncion(int funcionID) throws CinemaException {
        for(Funcion funcion:funciones){
            if(funcion.getFuncionID() == funcionID){
                return funcion.getAsientosDisponibles();
            }
        }
        throw new CinemaException(FUNCIONES_NO_ENCONTRADAS.getMessage(), FUNCIONES_NO_ENCONTRADAS.getCode());
    }

    public List<FuncionDTO> getListaFuncionesPorFecha(Date fchFuncion) {
       List<Funcion> funcionList = funciones.stream()
                .filter(f -> f.getFecha().equals(fchFuncion))
                .toList();

         List<FuncionDTO> funcionDTOList = new ArrayList<>();
        for (Funcion funcion : funcionList) {
            FuncionDTO funcionDTO = funcionMapper.toDTOFuncion(funcion);
            funcionDTOList.add(funcionDTO);
        }
        return funcionDTOList;
    }

    public int peliculaMasVista() {
        // TODO implement here
        return 0;
    }

    public int diaDeLaSemanaConMenorVentas() {
        // TODO implement here
        return 0;
    }

    public void registrarFuncionPorGenero(FuncionDTO funcionDTO) throws CinemaException {
        if (validarFuncionExiste(funcionDTO)) {
            throw new CinemaException(FUNCION_YA_EXISTENTE.getMessage(), FUNCION_YA_EXISTENTE.getCode());
        }
        List<Entrada> entradas = new ArrayList<>();
        Funcion funcion = new Funcion(
                funcionDTO.getPelicula(),
                id.getAndIncrement(), funcionDTO.getHorario(),
                funcionDTO.getFecha(), entradas,
                funcionDTO.getSala(),
                funcionDTO.getSala().getAsientos()
        );
        funciones.add(funcion);
    }

    public List<Funcion> buscarPeliculaPorIdPelicula(int peliculaID) {
        List<Funcion> funcionesDeLaPelicula = new ArrayList<>();
        for (Funcion funcion : funciones) {
            if (funcion.getPeliculaID() == peliculaID) {
                funcionesDeLaPelicula.add(funcion);
            }
        }
        return funcionesDeLaPelicula;
    }

    public List<Funcion> buscarFuncionPorGeneroPelicula(TipoGenero genero) {
        List<Funcion> funcionesDeLaPelicula = new ArrayList<>();
        for (Funcion funcion : funciones) {
            if (funcion.getPelicula().getGeneroID() == genero) {
                funcionesDeLaPelicula.add(funcion);
            }
        }
        return funciones;
    }

    public Funcion buscarFuncionPorFechaYPelicula(Date fecha, String hora, String peliculaName) throws CinemaException {
        Funcion funcionEncontrada = new Funcion();
        for (Funcion funcion : funciones) {
            if (funcion.getFecha().equals(fecha)
                    && funcion.getHorario().equals(hora)
                    && funcion.getPelicula().getNombrePelicula().equals(peliculaName)) {
                funcionEncontrada = funcion;
                return funcionEncontrada;
            }
        }
        throw new CinemaException(FUNCIONES_NO_ENCONTRADAS.getMessage(), FUNCIONES_NO_ENCONTRADAS.getCode());
    }

    private boolean validarFuncionExiste(FuncionDTO funcionDTO) {
        return funciones.stream()
                .anyMatch(f -> f.getFecha().equals(funcionDTO.getFecha())
                        && f.getHorario().equals(funcionDTO.getHorario())
                        && f.getSala().equals(funcionDTO.getSala()));
    }
}