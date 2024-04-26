package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class StartComponent implements Command {

    Runtime runtime;
    int componentId;

    public StartComponent(Runtime runtime, int componentId) {
        this.runtime = runtime;
        this.componentId = componentId;
    }

    @Override
    public boolean execute() {
        return runtime.startComponent(componentId);
    }
}
