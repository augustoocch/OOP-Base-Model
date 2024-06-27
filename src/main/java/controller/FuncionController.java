package controller;

import model.business.cine.Funcion;
import model.constants.TipoGenero;
import model.dto.FuncionDTO;
import model.exception.CinemaException;
import java.util.*;
import static model.exception.ErrorCode.FUNCION_YA_EXISTENTE;

public class FuncionController {
    private List<Funcion> funciones;
    public static FuncionController instancia;

    private FuncionController() {
        funciones = new ArrayList<Funcion>();
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

    public int obtenerAsientosDisponiblePorFuncion(int funcionID) {
        int asientos = -1;
        return asientos;
    }

    public List<FuncionDTO> getListaFunciones(Date fchFuncion) {
        // TODO implement here
        return null;
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
        int id = generarRandomId();
        while (buscarFuncionPorId(id) != null) {
            id = generarRandomId();
        }
        if (validarFuncionExiste(funcionDTO)) {
            throw new CinemaException(FUNCION_YA_EXISTENTE.getMessage(), FUNCION_YA_EXISTENTE.getCode());
        }

        Funcion funcion = new Funcion(
                funcionDTO.getPelicula(),
                id, funcionDTO.getHorario(),
                funcionDTO.getFecha(),
                new ArrayList<>(),
                funcionDTO.getSala()
        );
        funciones.add(funcion);
    }

    public List<Funcion> buscarPeliculaPorFuncion(int peliculaID) {
        List<Funcion> funcionesDeLaPelicula = new ArrayList<>();
        for (Funcion funcion : funciones) {
            if (funcion.getPeliculaID() == peliculaID) {
                funcionesDeLaPelicula.add(funcion);
            }
        }
        return funcionesDeLaPelicula;
    }

    public List<Funcion> buscarPeliculaPorGenerosFuncion(TipoGenero genero) {
        List<Funcion> funcionesDeLaPelicula = new ArrayList<>();
        for (Funcion funcion : funciones) {
            if (funcion.getPelicula().getGeneroID() == genero) {
                funcionesDeLaPelicula.add(funcion);
            }
        }
        return funciones;
    }

    private boolean validarFuncionExiste(FuncionDTO funcionDTO) {
        return funciones.stream()
                .anyMatch(f -> f.getFecha().equals(funcionDTO.getFecha())
                        && f.getHorario().equals(funcionDTO.getHorario())
                        && f.getSala().equals(funcionDTO.getSala()));
    }

    private int generarRandomId() {
        Random random = new Random();
        return random.nextInt(1000);
    }

    private Funcion buscarFuncionPorId(int id) {
        for (Funcion funcion : funciones) {
            if (funcion.getFuncionID() == id) {
                return funcion;
            }
        }
        return null;
    }
}