package com.jsonmack.mcplugins.scheduler;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Jason MK on 2020-04-10 at 12:54 p.m.
 */
public abstract class TimedBukkitRunnable extends BukkitRunnable {

    private int ticks;

    @Override
    public final void run() {
        ticks++;
        onRun(ticks);
    }

    public abstract void onRun(int ticks);

}
