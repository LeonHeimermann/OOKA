package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class StopInstanceCommand implements Command {

    private final Runtime runtime;
    private final int componentId;
    private final int threadId;

    public StopInstanceCommand(Runtime runtime, int componentId, int threadId) {
        this.runtime = runtime;
        this.componentId = componentId;
        this.threadId = threadId;
    }

    @Override
    public boolean execute() {
        return runtime.stopComponentInstance(componentId, threadId);
    }
}
