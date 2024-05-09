package org.ooka;

import org.ooka.cli.Cli;
import org.ooka.cli.CliImpl;


public class Main {
    public static void main(String[] args) {
        Cli cli = new CliImpl();
        cli.start();
    }
}