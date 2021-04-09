package com.shichko.deliveryservice.exception;

public class DeliveryServiceException extends Exception {
    public DeliveryServiceException() {
    }

    public DeliveryServiceException(String message) {
        super(message);
    }

    public DeliveryServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeliveryServiceException(Throwable cause) {
        super(cause);
    }
}
