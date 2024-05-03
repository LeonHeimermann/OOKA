package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class PrintStatusCommand implements Command {
    Runtime runtime;
    int componentId;

    public PrintStatusCommand(Runtime runtime, int componentId) {
        this.runtime = runtime;
        this.componentId = componentId;
    }

    @Override
    public boolean execute() {
        String status = runtime.getStatus(componentId);
        if (status == null) {
            return false;
        }
        System.out.println(status);
        return true;
    }
}
