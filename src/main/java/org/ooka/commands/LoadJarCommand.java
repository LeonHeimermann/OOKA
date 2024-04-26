package org.ooka.commands;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class LoadJarCommand implements Command {

    private final String pathToJar;

    public LoadJarCommand(String pathToJar) {
        this.pathToJar = pathToJar;
    }

    @Override
    public void execute() {
        try (JarFile jarFile = new JarFile(pathToJar)) {
            var jarEntries = jarFile.entries();
            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};

            try (URLClassLoader classLoader = URLClassLoader.newInstance(urls)) {
                while (jarEntries.hasMoreElements()) {
                    var jarEntry = jarEntries.nextElement();
                    if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                        continue;
                    }

                    String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
                    className = className.replace('/', '.');
                    Class<?> c = classLoader.loadClass(className);
                    System.out.println("Loaded class " + className);
                }
            }
        } catch (java.io.IOException ioException) {
            System.out.println("Loading JAR File " + pathToJar + " failed!");
            System.out.println("Exception: " + ioException.getMessage());
        } catch (ClassNotFoundException notFoundException) {
            System.out.println("Loading Class failed");
            System.out.println("Exception: " + notFoundException.getMessage());
        }
    }
}
