package model.exception;

import lombok.Getter;

@Getter
public class CinemaException extends Exception{
    int code;
    public CinemaException(String message, int code) {
        super(message);
        this.code = code;
    }
}
