package com.jsonmack.mcplugins.one_versus_one.event.phase;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by Jason MK on 2020-03-18 at 1:18 p.m.
 */
public class OneVersusOneEventPhaseQueue implements Queue<OneVersusOneEventPhase> {

    private final Queue<OneVersusOneEventPhase> phases;

    public OneVersusOneEventPhaseQueue(Queue<OneVersusOneEventPhase> phases) {
        this.phases = phases;
    }

    @Override
    public int size() {
        return phases.size();
    }

    @Override
    public boolean isEmpty() {
        return phases.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return phases.contains(o);
    }

    @Override
    public Iterator<OneVersusOneEventPhase> iterator() {
        return phases.iterator();
    }

    @Override
    public Object[] toArray() {
        return phases.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return phases.toArray(a);
    }

    @Override
    public boolean add(OneVersusOneEventPhase oneVersusOneEventPhase) {
        return phases.add(oneVersusOneEventPhase);
    }

    @Override
    public boolean remove(Object o) {
        return phases.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return phases.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends OneVersusOneEventPhase> c) {
        return phases.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return phases.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return phases.retainAll(c);
    }

    @Override
    public void clear() {
        phases.clear();
    }

    @Override
    public boolean offer(OneVersusOneEventPhase oneVersusOneEventPhase) {
        return phases.offer(oneVersusOneEventPhase);
    }

    @Override
    public OneVersusOneEventPhase remove() {
        return phases.remove();
    }

    @Override
    public OneVersusOneEventPhase poll() {
        return phases.poll();
    }

    @Override
    public OneVersusOneEventPhase element() {
        return phases.element();
    }

    @Override
    public OneVersusOneEventPhase peek() {
        return phases.peek();
    }
}
