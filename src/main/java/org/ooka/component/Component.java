package org.ooka.component;

import org.ooka.types.State;

public class Component implements ComponentInstanceHandler {

    private int id;
    private String componentName;
    private State state;
    private ComponentInstanceHandler componentInstanceHandler;

    public Component(int id, String componentName) {
        this(id, componentName, State.INITIAL);
    }

    public Component(int id, String componentName, State state) {
        this.id = id;
        this.componentName = componentName;
        this.state = state;
        componentInstanceHandler = new ComponentInstanceHandlerImpl(this);
    }

    public int getId() {
        return id;
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

    @Override
    public void startComponent() {
        componentInstanceHandler.startComponent();
    }

    @Override
    public void stopInstanceById(int threadId) {
        componentInstanceHandler.stopInstanceById(threadId);
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
