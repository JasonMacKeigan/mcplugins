package org.jsonmack.chestowner.service;

import org.jsonmack.chestowner.chest.OwnedChest;

import java.util.*;

public class OwnedChestService {

    private final Map<UUID, Set<OwnedChest>> chests = new HashMap<>();

    public void add(OwnedChest chest) {
        chests.computeIfAbsent(chest.getUuid(), k -> new HashSet<>()).add(chest);
    }

}
