package org.jsonmack.chestowner;

import org.jsonmack.chestowner.service.OwnedChestService;

public class ChestOwnerContext {

    private final OwnedChestService ownedChestService;

    public ChestOwnerContext(OwnedChestService ownedChestService) {
        this.ownedChestService = ownedChestService;
    }

    public OwnedChestService getOwnedChestService() {
        return ownedChestService;
    }
}
