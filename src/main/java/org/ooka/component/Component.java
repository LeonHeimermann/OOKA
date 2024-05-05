package org.ooka.component;

import org.ooka.types.State;

import java.lang.reflect.Method;

public class Component implements ComponentInstanceHandler {

    private int id;
    private final String componentName;
    private State state;
    private final Method startMethod;
    private final Method stopMethod;
    private final Class<?> startingClass;
    private final ComponentInstanceHandler componentInstanceHandler;

    public Component(String componentName, Method startMethod, Method stopMethod, Class<?> startingClass) {
        this(componentName, State.INITIAL, startMethod, stopMethod, startingClass);
    }

    public Component(String componentName, State state, Method startMethod, Method stopMethod, Class startingClass) {
        this.componentName = componentName;
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
