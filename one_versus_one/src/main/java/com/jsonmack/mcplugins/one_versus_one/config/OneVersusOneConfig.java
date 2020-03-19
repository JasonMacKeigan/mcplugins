package com.jsonmack.mcplugins.one_versus_one.config;

/**
 * Created by Jason MK on 2020-03-18 at 8:59 p.m.
 */
public class OneVersusOneConfig {

    private final boolean messageGlobal;

    public OneVersusOneConfig(boolean messageGlobal) {
        this.messageGlobal = messageGlobal;
    }

    public OneVersusOneConfig withMessageGlobal(boolean messageGlobal) {
        return new OneVersusOneConfig(messageGlobal);
    }

    public boolean isMessageGlobal() {
        return messageGlobal;
    }
}
