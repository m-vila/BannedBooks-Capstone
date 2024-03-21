package org.martavila.bannedbooks.exceptions;

public class UnableToLoadInitDataException extends RuntimeException {
    public UnableToLoadInitDataException(String message){
        super(message);
    }
}
