package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class StartInstanceCommand implements Command {

    Runtime runtime;
    int componentId;

    public StartInstanceCommand(Runtime runtime, int componentId) {
        this.runtime = runtime;
        this.componentId = componentId;
    }

    @Override
    public boolean execute() {
        return runtime.startComponentInstance(componentId);
    }
}
