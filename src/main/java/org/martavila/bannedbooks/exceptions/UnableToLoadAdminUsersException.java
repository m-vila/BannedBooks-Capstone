package org.martavila.bannedbooks.exceptions;

public class UnableToLoadAdminUsersException extends RuntimeException {
    public UnableToLoadAdminUsersException(String message){
        super(message);
    }
}
