package com.jsonmack.mcplugins.one_versus_one.event.phase;

import com.jsonmack.mcplugins.one_versus_one.event.OneVersusOneEvent;

/**
 * Created by Jason MK on 2020-03-18 at 1:17 p.m.
 */
public class OneVersusOneEventActivePhase implements OneVersusOneEventPhase {

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public void onStart(OneVersusOneEvent event) {

    }

    @Override
    public void tick(OneVersusOneEvent event) {

    }

}
