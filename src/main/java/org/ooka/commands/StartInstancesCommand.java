package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class StartInstancesCommand implements Command {
    Runtime runtime;
    int componentId;
    int amount;

    public StartInstancesCommand(Runtime runtime, int componentId, int amount) {
        this.runtime = runtime;
        this.componentId = componentId;
        this.amount = amount;
    }

    @Override
    public boolean execute() {
        return runtime.startComponentInstances(componentId, amount);
    }
}
