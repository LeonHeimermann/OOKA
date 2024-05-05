package org.ooka.cli;

import org.ooka.commands.Command;
import org.ooka.commands.ListComponentsCommand;
import org.ooka.commands.LoadJarCommand;
import org.ooka.commands.StartComponent;
import org.ooka.runtime.Runtime;

import java.util.Scanner;

public class CliImpl implements Cli {
    @Override
    public void start() {
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
                    if ( executeCommand(new LoadJarCommand(parameter[1])) ){
                        System.out.println("successfully deployed component");
                    }
                }
            } else if (input.startsWith("start")) {
                var parameter = input.split("\\s+");
                if (parameter.length != 2) {
                    System.out.println("start erwartet genau eine komponenten-id");
                } else {
                    System.out.println("start is not yet implemented");
                }
            } else if (input.equals("list")) {
                executeCommand(new ListComponentsCommand());
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
