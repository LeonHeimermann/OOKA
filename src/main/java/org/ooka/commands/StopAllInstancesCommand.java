package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class StopAllInstancesCommand implements Command {
    Runtime runtime;
    int componentId;

    public StopAllInstancesCommand(Runtime runtime, int componentId) {
        this.runtime = runtime;
        this.componentId = componentId;
    }

    @Override
    public boolean execute() {
        return runtime.stopAllComponentInstances(componentId);
    }
}
