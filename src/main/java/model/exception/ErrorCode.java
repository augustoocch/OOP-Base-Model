package model.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    FUNCION_YA_EXISTENTE("Funcion ya existente",100),
    FUNCIONES_NO_ENCONTRADAS("Funciones no encontradas", 101),

    PELICULA_YA_EXISTENTE("Pelicula ya existente", 200),
    NO_HAY_PELICULAS("No hay peliculas registradas", 201),
    NO_HAY_FUNCIONES("No hay funciones registradas", 202),
    NO_HAY_ASIENTOS("No hay asientos disponibles", 203),
    ASIENTO_YA_OCUPADO("Asiento ya ocupado", 204),
    ASIENTOS_INVALIDOS("Asientos inválidos", 205),

    VALORES_NULOS("Todos los campos deben ser seleccionados", 300),
    DURACION_INVALIDA("Duración inválida", 301),

    OPERACION_EXITOSA("Operación exitosa", 400),
    OPERACION_FALLIDA("Operación fallida", 401),
    OPERACION_CANCELADA("Operación cancelada", 402);



    private final String message;
    private final int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }
}

