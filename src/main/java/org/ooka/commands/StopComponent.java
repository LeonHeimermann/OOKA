package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class StopComponent implements Command {

    Runtime runtime;
    int componentId;
    int threadId;

    public StopComponent(Runtime runtime, int componentId, int threadId) {
        this.runtime = runtime;
        this.componentId = componentId;
        this.threadId = threadId;
    }

    @Override
    public boolean execute() {
        return runtime.stopComponent(componentId, threadId);
    }
}
