package com.sb.sunsecho;

public class TooBroadQueryException extends RuntimeException {
    public TooBroadQueryException() {
    }

    public TooBroadQueryException(String message) {
        super(message);
    }

    public TooBroadQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooBroadQueryException(Throwable cause) {
        super(cause);
    }

    public TooBroadQueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
