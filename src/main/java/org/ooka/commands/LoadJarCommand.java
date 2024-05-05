package org.ooka.commands;

import org.ooka.component.Component;
import org.ooka.runtime.Runtime;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class LoadJarCommand implements Command {

    private final Runtime runtime;
    private final String pathToJar;
    private final String FILE_NAME_REGEX = "[\\\\/].*\\\\|\\\\|/(.*?)(?=\\.jar$)";

    public LoadJarCommand(Runtime runtime, String pathToJar) {
        this.runtime = runtime;
        this.pathToJar = pathToJar;
    }

    @Override
    public boolean execute() {
        try (JarFile jarFile = new JarFile(pathToJar)) {
            var jarEntries = jarFile.entries();
            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};

            try (URLClassLoader classLoader = URLClassLoader.newInstance(urls)) {
                AnalyzedClass startingClass = null;

                while (jarEntries.hasMoreElements()) {
                    var jarEntry = jarEntries.nextElement();
                    if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                        continue;
                    }

                    String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
                    className = className.replace('/', '.');
                    Class<?> c = classLoader.loadClass(className);

                    var analyzedClass = new AnalyzedClass(c);
                    if (analyzedClass.isStartingClass) {
                        if (startingClass == null) {
                            startingClass = analyzedClass;
                        } else {
                            throw new RuntimeException("Mehr als eine StarterClass gefunden");
                        }
                    }
                }

                if (startingClass == null) {
                    throw new RuntimeException("Keine StarterClass gefunden");
                } else {
                    String componentName = generateComponentName();
                    System.out.println(componentName);
                    Component newComponent = new Component(
                            componentName,
                            startingClass.getStartMethod(),
                            startingClass.getStopMethod(),
                            startingClass.getStartingClass());
                    runtime.addComponent(newComponent);
                }
            }
            return true;
        } catch (java.io.IOException ioException) {
            System.out.println("Loading JAR File " + pathToJar + " failed!");
            System.out.println("Exception: " + ioException.getMessage());
            return false;
        } catch (ClassNotFoundException notFoundException) {
            System.out.println("Loading Class failed");
            System.out.println("Exception: " + notFoundException.getMessage());
            return false;
        }
    }

    private String generateComponentName() {
        int indexSlash = pathToJar.lastIndexOf("\\");
        int indexJar = pathToJar.indexOf(".jar");
        return pathToJar.substring(indexSlash + 1, indexJar);
    }

    private class AnalyzedClass {
        private final Class<?> startingClass;
        private Method startMethod;
        private Method stopMethod;
        private boolean isStartingClass;

        public AnalyzedClass(Class<?> startingClass) {
            this.startingClass = startingClass;
            analyzeClass(startingClass);
        }

        public Method getStartMethod() {
            return startMethod;
        }

        public Method getStopMethod() {
            return stopMethod;
        }

        public Class<?> getStartingClass() {
            return startingClass;
        }

        public boolean isStartingClass() {
            return isStartingClass;
        }

        private void analyzeClass(Class c) {
            for (var method : c.getMethods()) {
                for (var annotation : method.getDeclaredAnnotations()) {
                    if (annotation.annotationType().getSimpleName().equals("Start")) {
                        if (startMethod == null) {
                            startMethod = method;
                        } else {
                            throw new RuntimeException("Mehr als eine Startmethode gefunden!");
                        }
                    }
                    if (annotation.annotationType().getSimpleName().equals("Stop")) {
                        if (stopMethod == null) {
                            stopMethod = method;
                        } else {
                            throw new RuntimeException("Mehr als eine Startmethode gefunden!");
                        }
                    }
                }
            }

            isStartingClass = (startMethod != null && stopMethod != null);
        }
    }
}
