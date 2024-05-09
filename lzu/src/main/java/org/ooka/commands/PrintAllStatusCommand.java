package org.ooka.commands;

import org.ooka.runtime.Runtime;

import java.util.List;

public class PrintAllStatusCommand implements Command {

    private final Runtime runtime;

    public PrintAllStatusCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    @Override
    public boolean execute() {
        List<String> statusList = runtime.getAllStatus();
        if (statusList .isEmpty()) {
            return false;
        }
        statusList.forEach(entry -> System.out.printf("\t%s\n", entry));
        return true;
    }

}
