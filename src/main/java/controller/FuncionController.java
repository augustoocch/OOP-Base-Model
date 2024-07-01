package controller;

import config.CineConfig;
import model.business.cine.Funcion;
import model.business.cine.Sala;
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
    private SucursalController sucursalController;
    private List<Sala> salas;
    private List<String> horarios;

    private FuncionController() {
        funciones = new ArrayList<Funcion>();
        id = new AtomicInteger(0);
        config = CineConfig.getInstance();
        sucursalController = SucursalController.obtenerInstancia();
        funcionMapper = new FuncionMapper();
        horarios = config.getListadoHorasCine();
        salas = sucursalController.obtenerSalas();
    }

    public static FuncionController obtenerInstancia() {
        if (instancia == null) {
            instancia = new FuncionController();
        }
        return instancia;
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

    public void registrarFuncionPorGenero(FuncionDTO funcionDTO) throws CinemaException {
        if (validarFuncionExiste(funcionDTO)) {
            throw new CinemaException(FUNCION_YA_EXISTENTE.getMessage(), FUNCION_YA_EXISTENTE.getCode());
        }
        List<Entrada> entradas = new ArrayList<>();
        Funcion funcion = new Funcion(
                id.getAndIncrement(),
                funcionDTO.getPelicula(), funcionDTO.getHorario(),
                funcionDTO.getFecha(), entradas,
                funcionDTO.getSala(),
                funcionDTO.getSala().getAsientos()
        );
        funciones.add(funcion);
    }

    public List<FuncionDTO> obtenerFuncionesPorNombrePelicula(String nombrePelicula) {
        List<FuncionDTO> funcionesDeLaPelicula = new ArrayList<>();
        for (Funcion funcion : funciones) {
            if (funcion.getPelicula().getNombrePelicula().equals(nombrePelicula)) {
                FuncionDTO funcionFound = funcionMapper.toDTOFuncion(funcion);
                funcionesDeLaPelicula.add(funcionFound);
            }
        }
        return funcionesDeLaPelicula;
    }

    public FuncionDTO obtenerFuncionPorId(int funcionID) {
        FuncionMapper funcionMapper = new FuncionMapper();
        Funcion funcion=  funciones.stream()
                .filter(f -> f.getFuncionID() == funcionID)
                .findFirst()
                .orElse(null);
        return funcionMapper.toDTOFuncion(funcion);
    }

    public List<FuncionDTO> buscarFuncionesPorIdPelicula(int peliculaID) {
        List<FuncionDTO> funcionesDeLaPelicula = new ArrayList<>();
        for (Funcion funcion : funciones) {
            if (funcion.getPelicula().getPeliculaID() == peliculaID) {
                FuncionDTO funcionFound = funcionMapper.toDTOFuncion(funcion);
                funcionesDeLaPelicula.add(funcionFound);
            }
        }
        return funcionesDeLaPelicula;
    }

    public List<FuncionDTO> buscarFuncionPorGeneroPelicula(TipoGenero genero) {
        List<FuncionDTO> funcionesDeLaPelicula = new ArrayList<>();
        for (Funcion funcion : funciones) {
            if (funcion.getPelicula().getGeneroID() == genero) {
                FuncionDTO funcionFound = funcionMapper.toDTOFuncion(funcion);
                funcionesDeLaPelicula.add(funcionFound);
            }
        }
        return funcionesDeLaPelicula;
    }

    public Funcion buscarFuncionPorFechaYPelicula(Date fecha, String hora, String peliculaName) throws CinemaException {
        for (Funcion funcion : funciones) {
            if (funcion.getFecha().equals(fecha)
                    && funcion.getHorario().equals(hora)
                    && funcion.getPelicula().getNombrePelicula().equals(peliculaName)) {
                return funcion;
            }
        }
        throw new CinemaException(FUNCIONES_NO_ENCONTRADAS.getMessage(), FUNCIONES_NO_ENCONTRADAS.getCode());
    }

    public List<Sala> obtenerSalas() {
        return salas;
    }

    public List<String> obtenerHorarios() {
        return horarios;
    }

    private boolean validarFuncionExiste(FuncionDTO funcionDTO) {
        return funciones.stream()
                .anyMatch(f -> f.getFecha().equals(funcionDTO.getFecha())
                        && f.getHorario().equals(funcionDTO.getHorario())
                        && f.getSala().equals(funcionDTO.getSala()));
    }

    public static void eliminarInstancia(){
        instancia = null;
    }
}