package com.jsonmack.mcplugins.bed_teleport.command;

import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Jason MK on 2020-03-20 at 6:15 p.m.
 */
@RunWith(JUnit4.class)
public class BedTeleportConfigCommandTest {

    private static YamlConfiguration configuration;

    private static BedTeleportConfig config;

    @BeforeClass
    public static void before() throws IOException, InvalidConfigurationException {
        ConfigurationSerialization.registerClass(BedTeleportConfig.class);

        configuration = new YamlConfiguration();

        configuration.load(Paths.get("src", "main", "resources", "config.yml").toFile());

        config = configuration.getSerializable("config", BedTeleportConfig.class);
    }

    @Before
    public void assertConfigNonNull() {
        Assert.assertNotNull(config);
    }

    @Test
    public void assertTab() {
//        List<String> resultsFromNone = new Config.results(config, new String[] { "" });
//
//        Assert.assertEquals(config.getClass().getDeclaredFields().length, resultsFromNone.size());
//
//        List<String> resultsFromOne = BedTeleportConfigCommand.results(config, new String[] { resultsFromNone.get(0), "" });
//
//        System.out.println("results from one: " + resultsFromOne);
    }

}
