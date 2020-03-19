package com.jsonmack.mcplugins.one_versus_one.event;

import com.google.common.base.Preconditions;
import com.jsonmack.mcplugins.one_versus_one.OneVersusOnePlugin;
import com.jsonmack.mcplugins.one_versus_one.event.phase.OneVersusOneEventPhase;
import com.jsonmack.mcplugins.one_versus_one.event.phase.OneVersusOneEventPhaseQueue;
import com.jsonmack.mcplugins.one_versus_one.tick.TickUnit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jason MK on 2020-03-18 at 1:14 p.m.
 */
public class OneVersusOneEvent extends BukkitRunnable {

    private final OneVersusOnePlugin plugin;

    private final Set<UUID> players;

    private final LocalDateTime timestamp;

    private final Duration lifespan;

    private final OneVersusOneEventPhaseQueue phaseQueue;

    private OneVersusOneEventPhase phase;

    private BukkitTask task;

    public OneVersusOneEvent(OneVersusOnePlugin plugin, Set<UUID> players, LocalDateTime timestamp, Duration lifespan, OneVersusOneEventPhaseQueue phaseQueue) {
        Preconditions.checkArgument(!phaseQueue.isEmpty(), "The event has no phases.");

        this.plugin = plugin;
        this.players = players;
        this.timestamp = timestamp;
        this.lifespan = lifespan;
        this.phaseQueue = phaseQueue;

        phase = phaseQueue.poll();
    }

    @Override
    public void run() {
        phase.tick(this);

        if (phase.isComplete()) {
            if (phaseQueue.isEmpty()) {
                end();
            } else {
                phase = phaseQueue.poll();
                phase.onStart(this);
            }
        }
    }

    private void end() {

    }

    public void start() {
        if (task != null) {
            throw new IllegalStateException("Task has already been started.");
        }
        task = runTaskTimer(plugin, 0, TickUnit.toTick(lifespan.toNanos(), TimeUnit.NANOSECONDS));
    }

    public Set<UUID> getPlayers() {
        return players;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Duration getLifespan() {
        return lifespan;
    }

    public OneVersusOneEventPhaseQueue getPhaseQueue() {
        return phaseQueue;
    }
}
