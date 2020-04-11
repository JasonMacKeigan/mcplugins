package com.jsonmack.mcplugins.config.field;

/**
 * Created by Jason MK on 2020-03-20 at 7:16 p.m.
 */
public class ConfigFieldAcceptanceResult {

    private static final String NO_MESSAGE = "";

    private final boolean acceptable;

    private final String unacceptableMessage;

    public ConfigFieldAcceptanceResult(boolean acceptable, String unacceptableMessage) {
        this.acceptable = acceptable;
        this.unacceptableMessage = unacceptableMessage;
    }

    public static ConfigFieldAcceptanceResult ok() {
        return new ConfigFieldAcceptanceResult(true, NO_MESSAGE);
    }

    public static ConfigFieldAcceptanceResult unacceptable(String message) {
        return new ConfigFieldAcceptanceResult(false, message);
    }

    public static ConfigFieldAcceptanceResult unacceptableF(String message, Object... values) {
        return new ConfigFieldAcceptanceResult(false, String.format(message, values));
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
