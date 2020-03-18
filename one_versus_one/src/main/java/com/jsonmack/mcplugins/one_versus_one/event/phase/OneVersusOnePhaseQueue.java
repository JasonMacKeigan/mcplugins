package com.jsonmack.mcplugins.one_versus_one.event.phase;

import java.util.Queue;

/**
 * Created by Jason MK on 2020-03-18 at 1:18 p.m.
 */
public class OneVersusOnePhaseQueue {

    private final Queue<OneVersusOneEventPhase> phases;

    public OneVersusOnePhaseQueue(Queue<OneVersusOneEventPhase> phases) {
        this.phases = phases;
    }


}
