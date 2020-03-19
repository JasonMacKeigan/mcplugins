package com.jsonmack.mcplugins.one_versus_one.tick;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jason MK on 2020-03-18 at 1:46 p.m.
 */
public final class TickUnit {

    private static final long TICKS_TO_MILLIS = 50;

    public static long toTick(long duration, TimeUnit durationUnit) {
        return TimeUnit.MILLISECONDS.convert(duration, durationUnit) / TICKS_TO_MILLIS;
    }

}
