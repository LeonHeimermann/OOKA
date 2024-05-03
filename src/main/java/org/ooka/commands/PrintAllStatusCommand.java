package org.ooka.commands;

import org.ooka.runtime.Runtime;

import java.util.List;

public class PrintAllStatusCommand implements Command {

    private final Runtime runtime;
    private final int componentId;

    public PrintAllStatusCommand(Runtime runtime, int componentId) {
        this.runtime = runtime;
        this.componentId = componentId;
    }

    @Override
    public boolean execute() {
        List<String> statusList = runtime.getAllStatus();
        if (statusList .isEmpty()) {
            return false;
        }
        statusList.forEach(System.out::println);
        return true;
    }

}
