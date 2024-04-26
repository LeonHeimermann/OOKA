package org.ooka.cli;

import org.ooka.commands.Command;
import org.ooka.commands.LoadJarCommand;

import java.util.Arrays;
import java.util.Scanner;

public class CliImpl implements Cli{
    @Override
    public void start() {
        System.out.println("--- Beste LZU der Welt ---");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("help")) {
                printHelp();
            }
            else if (input.equals("exit")){
                System.out.println("LZU wird heruntergefahren...");
                return;
            } else if (input.startsWith("deploy")) {
                var parameter = input.split("\\s+");
                if (parameter.length != 2) {
                    System.out.println("deploy erwartet genau eine pfadangabe");
                } else {
                    executeCommand(new LoadJarCommand(parameter[1]));
                }
            } else {
                System.out.println("'" + input + "' ist kein gültiges kommando. für eine Kommandoübersicht 'help' eingeben.");
            }
        }
    }

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    private void printHelp() {
        System.out.println("deploy [pfad]:\tKomponente deployen");
        System.out.println("help:\t\t\tHilfe anzeigen");
        System.out.println("exit:\t\t\tLZU beenden");
    }
}
