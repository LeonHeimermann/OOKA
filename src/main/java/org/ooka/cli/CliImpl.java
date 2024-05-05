package org.ooka.cli;

import org.ooka.commands.*;
import org.ooka.runtime.RuntimeImpl;

import java.util.Scanner;

public class CliImpl implements Cli {
    @Override
    public void start() {
        var runtime = RuntimeImpl.getInstance();
        System.out.println("--- Beste LZU der Welt ---");
        printHelp();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("help")) {
                printHelp();
            }
            else if (input.equals("exit")){
                System.out.println("LZU wird heruntergefahren...");
                System.exit(0);
            } else if (input.startsWith("deploy")) {
                var parameter = input.split("\\s+");
                if (parameter.length != 2) {
                    System.out.println("deploy erwartet genau eine pfadangabe");
                } else {
                    if ( executeCommand(new LoadJarCommand(runtime, parameter[1])) ){
                        System.out.println("successfully deployed component");
                    }
                }
            } else if (input.startsWith("start")) {
                var parameter = input.split("\\s+");
                if (parameter.length != 2) {
                    System.out.println("start erwartet genau eine komponenten-id");
                } else {
                    try {
                        int componentId = Integer.parseInt(parameter[1]);
                        Command startInstanceCommand = new StartInstanceCommand(runtime, componentId);
                        startInstanceCommand.execute();
                        System.out.println("Successfully started component");
                    } catch (NumberFormatException e) {
                        System.out.println("Id could not be parsed");
                    }
                }
            } else if (input.equals("list")) {
                executeCommand(new ListComponentsCommand());
            } else if (input.startsWith("status")) {
                var parameter = input.split("\\s+");
                if (parameter.length == 1) {
                    Command printAllStatusCommand = new PrintAllStatusCommand(runtime);
                    printAllStatusCommand.execute();
                } else {
                    int componentId = Integer.parseInt(parameter[1]);
                    Command printStatusCommand = new PrintStatusCommand(runtime, componentId);
                    printStatusCommand.execute();
                }
            } else {
                System.out.println("'" + input + "' ist kein gültiges kommando. für eine Kommandoübersicht 'help' eingeben.");
            }
        }
    }

    @Override
    public boolean executeCommand(Command command) {
        return command.execute();
    }

    private void printHelp() {
        System.out.println("deploy [pfad]:\tKomponente deployen");
        System.out.println("start [id]:\t\tKomponente starten");
        System.out.println("list: \t\t\tKomponenten auflisten");
        System.out.println("help:\t\t\tHilfe anzeigen");
        System.out.println("exit:\t\t\tLZU beenden");
    }
}
