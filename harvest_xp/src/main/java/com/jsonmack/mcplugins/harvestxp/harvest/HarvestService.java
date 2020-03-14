package com.jsonmack.mcplugins.harvestxp.harvest;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason MK on 2020-03-13 at 2:13 p.m.
 */
public class HarvestService {

    private final Map<Material, Harvested> harvested = new HashMap<>();

    public void harvest(Block block, Player harvester, int amountRequired, int experience) {
        Material material = block.getType();

        Harvested harvested = this.harvested.compute(material, (key, value) -> value == null
                ? new Harvested(material,  1)
                : new Harvested(value.getType(), value.getAmount() + 1));

        if (harvested.getAmount() >= amountRequired) {
            this.harvested.remove(material);

            World world = harvester.getWorld();

            ExperienceOrb orb = world.spawn(block.getLocation(), ExperienceOrb.class);

            orb.setExperience(experience);
            orb.setGlowing(true);
        }
    }


}
