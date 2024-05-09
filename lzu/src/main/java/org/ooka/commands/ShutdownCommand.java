package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class ShutdownCommand implements Command {

    private final Runtime runtime;

    public ShutdownCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public boolean execute() {
        runtime.shutdown();
        return true;
    }
}
