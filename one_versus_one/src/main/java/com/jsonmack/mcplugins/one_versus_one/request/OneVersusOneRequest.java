package com.jsonmack.mcplugins.one_versus_one.request;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Jason MK on 2020-03-18 at 12:59 p.m.
 */
public class OneVersusOneRequest {

    private final UUID source;

    private final UUID target;

    private final UUID id;

    private final LocalDateTime timestamp;

    private final Duration lifespan;

    public OneVersusOneRequest(UUID source, UUID target, UUID id, LocalDateTime timestamp, Duration lifespan) {
        this.source = source;
        this.target = target;
        this.id = id;
        this.timestamp = timestamp;
        this.lifespan = lifespan;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(timestamp.plus(lifespan));
    }

    public UUID getSource() {
        return source;
    }

    public UUID getTarget() {
        return target;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
