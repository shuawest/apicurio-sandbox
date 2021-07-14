package com.aregsandbox;

public class GatewayException extends Exception {
    private static final long serialVersionUID = 1L;

    public GatewayException(String message) {
        super(message);
    }

    public GatewayException(String message, Exception cause) {
        super(message, cause);
    }
}
