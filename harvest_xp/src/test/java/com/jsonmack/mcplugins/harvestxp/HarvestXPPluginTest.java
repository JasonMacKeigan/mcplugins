package com.jsonmack.mcplugins.harvestxp;

import com.jsonmack.mcplugins.harvestxp.config.HarvestConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestConfigCodec;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfigKey;
import com.jsonmack.mcplugins.harvestxp.config.HarvestToolConfigKey;
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
import java.util.Objects;

/**
 * Created by Jason MK on 2020-03-13 at 3:36 p.m.
 */
@RunWith(JUnit4.class)
public class HarvestXPPluginTest {

    private HarvestConfig config;

    private FileConfiguration fileConfiguration = new YamlConfiguration();

    @Before
    public void load() throws IOException, InvalidConfigurationException {
        fileConfiguration.load(Paths.get("src", "test", "resources", "config.yml").toFile());

        config = new HarvestConfigCodec().decode(fileConfiguration);
    }

    @Test
    public void assertToolReductionKeysExist() {
        Assert.assertTrue(HarvestToolConfigKey.getKeys().stream().allMatch(key -> fileConfiguration.isSet(key.getReductionKey())));
    }

    @Test
    public void assertHoeToolRequiredExists() {
        Assert.assertTrue(fileConfiguration.isSet(HarvestConfigCodec.HOE_TOOL_REQUIRED_KEY));
    }

    @Test
    public void assertConfigSize() {
        Assert.assertEquals(config.getMaterialConfigs().size(), HarvestMaterialConfigKey.getAllKeys().size());
    }

    @Test
    public void assertConfigMaterialsNonNull() {
        Assert.assertTrue(config.getMaterialConfigs().stream().noneMatch(config -> config.getMaterial() == null));
    }

    @Test
    public void assertToolConfigSize() {
        Assert.assertEquals(config.getToolConfigs().size(), HarvestToolConfigKey.getKeys().size());
    }

    @Test
    public void assertToolConfigsNonNull() {
        Assert.assertTrue(config.getToolConfigs().stream().allMatch(Objects::nonNull));
    }

    @Test
    public void assertHoeToolTypeReducingExists() {
        Assert.assertTrue(fileConfiguration.isSet(HarvestConfigCodec.HOE_TYPE_REDUCING_HARVEST_KEY));
    }
}
