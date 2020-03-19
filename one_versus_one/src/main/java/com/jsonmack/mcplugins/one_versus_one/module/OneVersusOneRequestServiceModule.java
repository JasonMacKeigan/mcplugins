package com.jsonmack.mcplugins.one_versus_one.module;

import com.google.inject.AbstractModule;
import com.jsonmack.mcplugins.one_versus_one.service.OneVersusOneRequestService;

/**
 * Created by Jason MK on 2020-03-18 at 5:01 p.m.
 */
public class OneVersusOneRequestServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(OneVersusOneRequestService.class)
                .asEagerSingleton();
    }

}
