package com.jsonmack.mcplugins.config;

/**
 * Created by Jason MK on 2020-03-20 at 7:16 p.m.
 */
public class ConfigListenerAcceptableResult {

    private static final String NO_MESSAGE = "";

    private final boolean acceptable;

    private final String unacceptableMessage;

    public ConfigListenerAcceptableResult(boolean acceptable, String unacceptableMessage) {
        this.acceptable = acceptable;
        this.unacceptableMessage = unacceptableMessage;
    }

    public static ConfigListenerAcceptableResult ok() {
        return new ConfigListenerAcceptableResult(true, NO_MESSAGE);
    }

    public static ConfigListenerAcceptableResult unacceptable(String message) {
        return new ConfigListenerAcceptableResult(false, message);
    }

    public static ConfigListenerAcceptableResult unacceptableF(String message, Object... values) {
        return new ConfigListenerAcceptableResult(false, String.format(message, values));
    }

    public static String getNoMessage() {
        return NO_MESSAGE;
    }

    public boolean isAcceptable() {
        return acceptable;
    }

    public String getUnacceptableMessage() {
        return unacceptableMessage;
    }
}
