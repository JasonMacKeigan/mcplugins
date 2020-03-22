package com.jsonmack.mcplugins.bed_teleport.config;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Jason MK on 2020-03-20 at 12:36 p.m.
 */
//@RunWith(JUnit4.class)
public class BedTeleportConfigTest {

//    private static YamlConfiguration configuration;
//
//    @BeforeClass
//    public static void before() throws IOException, InvalidConfigurationException {
//        ConfigurationSerialization.registerClass(BedTeleportConfig.class);
//
//        configuration = new YamlConfiguration();
//
//        configuration.load(Paths.get("src", "main", "resources", "config.yml").toFile());
//    }
//    @Test
//    public void assertConfigLoads() {
//        BedTeleportConfig config = configuration.getSerializable("config", BedTeleportConfig.class);
//
//        System.out.println("config: " + ReflectionToStringBuilder.toString(config));
//        Assert.assertNotNull(config);
//    }
}
