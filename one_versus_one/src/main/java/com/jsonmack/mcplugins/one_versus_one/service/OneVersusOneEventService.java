package com.jsonmack.mcplugins.one_versus_one.service;

import com.jsonmack.mcplugins.one_versus_one.event.OneVersusOneEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Jason MK on 2020-03-18 at 4:56 p.m.
 */
public class OneVersusOneEventService {

    private final Set<OneVersusOneEvent> events = new HashSet<>();

    public void add(OneVersusOneEvent event) {
        events.add(event);
    }

    public boolean contains(UUID uuid) {
        return events.stream().anyMatch(event -> event.getPlayers().stream().anyMatch(player -> player.equals(uuid)));
    }

}
