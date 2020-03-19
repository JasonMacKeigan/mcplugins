package com.jsonmack.mcplugins.one_versus_one.event.phase;

import com.jsonmack.mcplugins.one_versus_one.event.OneVersusOneEvent;

/**
 * Created by Jason MK on 2020-03-18 at 1:14 p.m.
 */
public interface OneVersusOneEventPhase {

    boolean isComplete();

    void onStart(OneVersusOneEvent event);

    void tick(OneVersusOneEvent event);

}
