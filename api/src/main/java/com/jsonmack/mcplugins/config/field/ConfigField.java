package com.jsonmack.mcplugins.config.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jason MK on 2020-03-20 at 6:55 p.m.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigField {

    Class<? extends ConfigFieldListener<?, ?>> value();

}
