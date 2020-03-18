package com.jsonmack.mcplugins.one_versus_one.event;

import com.jsonmack.mcplugins.one_versus_one.event.phase.OneVersusOneEventPhase;

/**
 * Created by Jason MK on 2020-03-18 at 1:14 p.m.
 */
public class OneVersusOneEvent {

    private OneVersusOneEventPhase phase;

    public OneVersusOneEvent(OneVersusOneEventPhase phase) {
        this.phase = phase;
    }


}
