package org.ooka.component;

import org.ooka.types.State;

import java.lang.reflect.Method;
import java.net.URLClassLoader;

public class Component implements ComponentInstanceHandler {

    private int id;
    private final String componentName;
    private final URLClassLoader classLoader;
    private State state;
    private final Method startMethod;
    private final Method stopMethod;
    private final Class<?> startingClass;
    private final ComponentInstanceHandler componentInstanceHandler;

    public Component(String componentName, URLClassLoader classLoader, Method startMethod, Method stopMethod, Class<?> startingClass) {
        this(componentName, classLoader, State.INITIAL, startMethod, stopMethod, startingClass);
    }

    public Component(String componentName, URLClassLoader classLoader, State state, Method startMethod, Method stopMethod, Class<?> startingClass) {
        this.componentName = componentName;
        this.classLoader = classLoader;
        this.state = state;
        componentInstanceHandler = new ComponentInstanceHandlerImpl(this);
        this.startMethod = startMethod;
        this.stopMethod = stopMethod;
        this.startingClass = startingClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public URLClassLoader getClassLoader() {
        return classLoader;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

    @Override
    public void startInstance() {
        componentInstanceHandler.startInstance();
    }

    @Override
    public void startInstances(int amount) {
        componentInstanceHandler.startInstances(amount);
    }

    @Override
    public void removeInstanceById(int threadId, boolean flagInterrupt) {
        componentInstanceHandler.removeInstanceById(threadId, flagInterrupt);
    }

    @Override
    public void stopAllInstances() {
        componentInstanceHandler.stopAllInstances();
    }

    @Override
    public void checkIsRunning() {
        componentInstanceHandler.checkIsRunning();
    }

    public String toString() {
        return String.format("id: %s, name: %s, state: %s", id, componentName, state);
    }

}
