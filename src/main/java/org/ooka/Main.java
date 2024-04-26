package org.ooka;

import org.ooka.cli.Cli;
import org.ooka.cli.CliImpl;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {
    public static void main(String[] args) {
        Cli cli = new CliImpl();
        cli.start();
    }
}