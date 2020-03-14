package com.jsonmack.mcplugins.harvestxp;

import com.jsonmack.mcplugins.harvestxp.config.HarvestConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfigKey;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Paths;

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

        config = HarvestConfig.read(fileConfiguration);

        config.getMaterialConfigs().forEach(config -> System.out.println(ReflectionToStringBuilder.toString(config, ToStringStyle.DEFAULT_STYLE)));
    }

    @Test
    public void assertHoeToolRequiredExists() {
        Assert.assertTrue(fileConfiguration.isSet("hoe_tool_required"));
    }

    @Test
    public void assertConfigSize() {
        Assert.assertEquals(config.getMaterialConfigs().size(), HarvestMaterialConfigKey.getAllKeys().size());
    }

    @Test
    public void assertConfigMaterialsNonNull() {
        Assert.assertTrue(config.getMaterialConfigs().stream().noneMatch(config -> config.getMaterial() == null));
    }

}
