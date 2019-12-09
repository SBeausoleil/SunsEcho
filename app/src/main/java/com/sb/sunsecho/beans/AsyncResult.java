package com.sb.sunsecho.beans;

public class AsyncResult<T> {

    private T value;
    private Exception exception;

    public AsyncResult(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean hasException() {
        return exception != null;
    }
}
