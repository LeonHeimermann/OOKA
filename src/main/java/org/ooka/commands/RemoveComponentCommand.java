package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class RemoveComponentCommand implements Command {

    Runtime runtime;
    int componentId;

    public RemoveComponentCommand(Runtime runtime, int componentId) {
        this.runtime = runtime;
        this.componentId = componentId;
    }

    @Override
    public boolean execute() {
        return runtime.removeComponent(componentId);
    }
}
