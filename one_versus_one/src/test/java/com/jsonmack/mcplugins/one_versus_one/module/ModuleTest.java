package com.jsonmack.mcplugins.one_versus_one.module;

import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jsonmack.mcplugins.one_versus_one.config.OneVersusOneConfig;
import com.jsonmack.mcplugins.one_versus_one.service.OneVersusOneConfigService;
import com.jsonmack.mcplugins.one_versus_one.service.OneVersusOneEventService;
import com.jsonmack.mcplugins.one_versus_one.service.OneVersusOneRequestService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-03-18 at 8:36 p.m.
 */
@RunWith(JUnit4.class)
public class ModuleTest {

    private static Injector injector;

    private static final File FOLDER = new File("src/main/resources");

    private static final String FILE_NAME = "config.json";

    @BeforeClass
    public static void before() {
        injector = Guice.createInjector(
                new OneVersusOneEventServiceModule(),
                new OneVersusOneEventServiceModule(),
                new OneVersusOneConfigServiceModule(FOLDER, FILE_NAME));
    }

    @Test
    public void assertSingletonsSame() {
        OneVersusOneEventService eventService = injector.getInstance(OneVersusOneEventService.class);

        OneVersusOneRequestService requestService = injector.getInstance(OneVersusOneRequestService.class);

        OneVersusOneEventService eventServiceTwo = injector.getInstance(OneVersusOneEventService.class);

        OneVersusOneRequestService requestServiceTwo = injector.getInstance(OneVersusOneRequestService.class);

        Assert.assertEquals(eventService, eventServiceTwo);

        Assert.assertEquals(requestService, requestServiceTwo);
    }

    @Test
    public void assertConfig() {
        OneVersusOneConfigService configService = injector.getInstance(OneVersusOneConfigService.class);

        Assert.assertNotNull(configService);

        Assert.assertNotNull(configService.getConfig());
    }

    @Test
    public void assertConfigWrite() {
        OneVersusOneConfigService configService = injector.getInstance(OneVersusOneConfigService.class);

        boolean invertedValue = !configService.getConfig().isMessageGlobal();

        configService.setConfig(new OneVersusOneConfig(invertedValue));
        try {
            configService.write();

            String content = String.join("", Files.readAllLines(FOLDER.toPath().resolve(FILE_NAME)));

            OneVersusOneConfig writtenConfig = new GsonBuilder().create().fromJson(content, OneVersusOneConfig.class);

            Assert.assertEquals(invertedValue, writtenConfig.isMessageGlobal());

            //UNDO? oof
            configService.setConfig(new OneVersusOneConfig(!invertedValue));
            configService.write();
        } catch (IOException e) {
            throw new RuntimeException("Failed, unable to write.", e);
        }
    }
}
