package br.com.sobreiraromulo.exceptions;

public class RequiredObjectIsNullException extends RuntimeException {
    public RequiredObjectIsNullException(String message) {
        super(message);
    }
}
