package com.jsonmack.mcplugins.silktouch_spawner.config.field;

import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.impl.IntegerConfigFieldListener;
import com.jsonmack.mcplugins.silktouch_spawner.config.SilkTouchSpawnerConfig;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.*;

/**
 * Created by Jason MK on 2020-04-15 at 4:21 p.m.
 */
public class AmountOfSpawnsFieldListener implements IntegerConfigFieldListener<SilkTouchSpawnerConfig> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(Integer value) {
        return value <= 0 ? unacceptable("The amount of spawns must be greater than zero.") : ok();
    }

    @Override
    public SilkTouchSpawnerConfig modify(SilkTouchSpawnerConfig config, Integer value) {
        return config.withAmountOfSpawns(value);
    }
}
