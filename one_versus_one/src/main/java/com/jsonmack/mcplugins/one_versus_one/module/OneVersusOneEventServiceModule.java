package com.jsonmack.mcplugins.one_versus_one.module;

import com.google.inject.AbstractModule;
import com.jsonmack.mcplugins.one_versus_one.service.OneVersusOneEventService;

/**
 * Created by Jason MK on 2020-03-18 at 5:01 p.m.
 */
public class OneVersusOneEventServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(OneVersusOneEventService.class)
                .asEagerSingleton();
    }
}
