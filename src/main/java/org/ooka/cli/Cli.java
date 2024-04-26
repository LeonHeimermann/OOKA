package org.ooka.cli;

import org.ooka.commands.Command;

public interface Cli {
    void start();

    void executeCommand(Command command);
}
