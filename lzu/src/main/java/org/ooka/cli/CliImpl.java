package org.ooka.cli;

import org.ooka.commands.*;
import org.ooka.runtime.Runtime;
import org.ooka.runtime.RuntimeImpl;

import java.util.Scanner;

public class CliImpl implements Cli {
    @Override
    public void start() {
        System.out.println("--- Beste LZU der Welt ---");
        printHelp();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            handleCommand(command);
        }
    }

    private void handleCommand(String command) {
        Runtime runtime = RuntimeImpl.getInstance();
        String[] inputTokens = command.split(" ", 2);
        switch (inputTokens[0]) {
            case "help":
                printHelp();
                break;
            case "exit":
                handleExit(runtime);
                break;
            case "deploy":
                handleDeploy(inputTokens, runtime);
                break;
            case "start":
                handleStart(inputTokens, runtime);
                break;
            case "stop":
                handleStop(inputTokens, runtime);
                break;
            case "list":
                executeCommand(new ListComponentsCommand());
                break;
            case "status":
                handleStatus(inputTokens, runtime);
                break;
            case "remove":
                handleRemove(inputTokens, runtime);
                break;
            default:
                System.out.println("'" + command + "' ist kein gültiges kommando. für eine Kommandoübersicht 'help' eingeben.");
                break;
        }
    }

    @Override
    public boolean executeCommand(Command command) {
        return command.execute();
    }

    private void printHelp() {
        System.out.println("deploy [pfad]:\tKomponente deployen");
        System.out.println("start [id]:\t\tKomponente starten");
        System.out.println("stop [id]:\t\tKomponente stoppen");
        System.out.println("list: \t\t\tKomponenten auflisten");
        System.out.println("status: \t\t\tStatus der Komponenten auflisten");
        System.out.println("status [id]: \t\t\tStatus einer Komponente auflisten");
        System.out.println("remove [id]: \t\t\tKomponente entfernen");
        System.out.println("help:\t\t\tHilfe anzeigen");
        System.out.println("exit:\t\t\tLZU beenden");
    }

    private void handleExit(Runtime runtime) {
        System.out.println("LZU wird heruntergefahren...");
        executeCommand(new ShutdownCommand(runtime));
        System.exit(0);
    }

    private void handleDeploy(String[] parameter, Runtime runtime) {
        if (parameter.length != 2) {
            System.out.println("deploy erwartet genau eine pfadangabe");
        } else {
            if (executeCommand(new LoadJarCommand(runtime, parameter[1]))) {
                System.out.println("successfully deployed component");
            }
        }
    }

    private void handleStart(String[] parameter, Runtime runtime) {
        if (parameter.length != 2) {
            System.out.println("start erwartet genau eine komponenten-id");
        } else {
            try {
                int componentId = Integer.parseInt(parameter[1]);
                Command startInstanceCommand = new StartInstanceCommand(runtime, componentId);
                if (startInstanceCommand.execute()) {
                    System.out.println("Successfully started component");
                }
            } catch (NumberFormatException e) {
                System.out.println("Id could not be parsed");
            }
        }
    }

    private void handleStop(String[] parameter, Runtime runtime) {
        if (parameter.length != 2) {
            System.out.println("stop erwartet genau eine komponenten-id");
        } else {
            try {
                int componentId = Integer.parseInt(parameter[1]);
                Command stopAllInstancesCommand = new StopAllInstancesCommand(runtime, componentId);
                if (stopAllInstancesCommand.execute()) {
                    System.out.println("Successfully stopped component");
                }
            } catch (NumberFormatException e) {
                System.out.println("Id could not be parsed");
            }
        }
    }

    private void handleStatus(String[] parameter, Runtime runtime) {
        if (parameter.length == 1) {
            Command printAllStatusCommand = new PrintAllStatusCommand(runtime);
            printAllStatusCommand.execute();
        } else {
            int componentId = Integer.parseInt(parameter[1]);
            Command printStatusCommand = new PrintStatusCommand(runtime, componentId);
            printStatusCommand.execute();
        }
    }

    private void handleRemove(String[] parameter, Runtime runtime) {
        if (parameter.length != 2) {
            System.out.println("remove erwartet genau eine komponenten-id");
        } else {
            try {
                int componentId = Integer.parseInt(parameter[1]);
                Command removeComponentCommand = new RemoveComponentCommand(runtime, componentId);
                removeComponentCommand.execute();
                System.out.println("Successfully removed component");
            } catch (NumberFormatException e) {
                System.out.println("Id could not be parsed");
            }
        }
    }
}
