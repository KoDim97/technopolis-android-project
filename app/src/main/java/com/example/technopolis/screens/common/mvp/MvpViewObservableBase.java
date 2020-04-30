package com.example.technopolis.screens.common.mvp;

import java.util.HashSet;
import java.util.Set;

public abstract class MvpViewObservableBase<ListenerType> extends MvpViewBase
        implements MvpViewObservable<ListenerType> {

    private Set<ListenerType> listeners = new HashSet<>();

    @Override public final void registerListener(ListenerType listener) {
        listeners.add(listener);
    }

    @Override public final void unregisterListener(ListenerType listener) {
        listeners.remove(listener);
    }

    protected final Set<ListenerType> getListeners() {
        return listeners;
    }
}
