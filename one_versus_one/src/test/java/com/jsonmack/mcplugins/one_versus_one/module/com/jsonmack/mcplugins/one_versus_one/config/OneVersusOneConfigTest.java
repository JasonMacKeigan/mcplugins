package com.jsonmack.mcplugins.one_versus_one.module.com.jsonmack.mcplugins.one_versus_one.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Jason MK on 2020-03-18 at 9:41 p.m.
 */
@RunWith(JUnit4.class)
public class OneVersusOneConfigTest {

    private static YamlConfiguration configuration;

    @BeforeClass
    public static void init() {
        Path path = Paths.get("src", "main", "resources", "config.yml");

        configuration = YamlConfiguration.loadConfiguration(path.toFile());
    }

    @Test
    public void assertConfig() {

    }

}
