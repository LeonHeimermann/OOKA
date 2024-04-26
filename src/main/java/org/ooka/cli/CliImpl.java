package org.ooka.cli;

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
            } else if (input.startsWith("load")) {
                var parameter = input.split("\\s+");
                if (parameter.length != 2) {
                    System.out.println("load erwartet genau eine pfadangabe");
                } else {
                    var loadJarCommand = new LoadJarCommand(parameter[1]);
                    loadJarCommand.execute();
                }
            } else {
                System.out.println("'" + input + "' ist kein gültiges kommando. für eine Kommandoübersicht 'help' eingeben.");
            }
        }
    }

    @Override
    public void executeCommand() {

    }

    private void printHelp() {
        System.out.println("help:\tHilfe anzeigen");
        System.out.println("exit:\tLZU beenden");
    }
}
