package com.hlsp.hlsp_site;

public class BadCredentialsException extends Exception{
    public BadCredentialsException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
