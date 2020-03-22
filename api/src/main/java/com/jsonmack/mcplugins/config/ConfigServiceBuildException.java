package com.jsonmack.mcplugins.config;

/**
 * Created by Jason MK on 2020-03-20 at 8:12 p.m.
 */
public class ConfigServiceBuildException extends RuntimeException {

    public ConfigServiceBuildException() {
    }

    public ConfigServiceBuildException(String message) {
        super(message);
    }

    public ConfigServiceBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigServiceBuildException(Throwable cause) {
        super(cause);
    }

    public ConfigServiceBuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
