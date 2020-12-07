package com.shirley.animal.data;

/**
 *
 * @author Chantal Shirley
 */
public class AnimalDataException extends Exception {
    /**
     * Creates a new instance of AnimalDataExceptionwithout detail
     * message.
     */
    public AnimalDataException() {
    }

    /**
     * Constructs an instance of AnimalDataExceptionwith the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AnimalDataException(String msg) {
        super(msg);
    }

    public AnimalDataException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AnimalDataException(Throwable cause) {
        super(cause);
    }

    public AnimalDataException(String msg, Throwable cause
            , boolean enableSuppression, boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }
}
