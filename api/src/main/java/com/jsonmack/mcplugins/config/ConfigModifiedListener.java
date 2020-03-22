package com.jsonmack.mcplugins.config;

/**
 * Created by Jason MK on 2020-03-20 at 11:45 p.m.
 */
public interface ConfigModifiedListener<T extends Config> {

    void onModify(T config);

}
