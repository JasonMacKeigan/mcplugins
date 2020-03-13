package com.jsonmack.mcplugins.harvestxp;

import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfigDecoder;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfigKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Created by Jason MK on 2020-03-13 at 3:36 p.m.
 */
@RunWith(JUnit4.class)
public class HarvestXPPluginTest {

    private Set<HarvestMaterialConfig> configs;

    @Before
    public void load() throws IOException, InvalidConfigurationException {
        HarvestMaterialConfigDecoder decoder = new HarvestMaterialConfigDecoder();

        FileConfiguration fileConfiguration = new YamlConfiguration();

        fileConfiguration.load(Paths.get("src", "test", "resources", "config.yml").toFile());

        configs = decoder.decodeAll(fileConfiguration);

        Assert.assertNotNull(configs);
    }

    @Test
    public void assertConfigSize() {
        Assert.assertEquals(configs.size(), HarvestMaterialConfigKey.getAllKeys().size());
    }

    @Test
    public void assertConfigMaterialsNonNull() {
        Assert.assertTrue(configs.stream().noneMatch(config -> config.getMaterial() == null));
    }

}
