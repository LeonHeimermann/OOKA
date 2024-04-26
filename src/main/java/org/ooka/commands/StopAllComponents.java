package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class StopAllComponents implements Command {
    Runtime runtime;
    int componentId;

    public StopAllComponents(Runtime runtime, int componentId) {
        this.runtime = runtime;
        this.componentId = componentId;
    }

    @Override
    public boolean execute() {
        return runtime.stopAllComponents(componentId);
    }
}
