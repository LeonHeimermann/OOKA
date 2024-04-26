package org.ooka.component;

import org.ooka.types.State;

import java.util.ArrayList;
import java.util.List;

public class Component {

    private int id;
    private String componentName;
    private State state;
    private List<Thread> instances;

    public Component(int id, String componentName) {
        this(id, componentName, State.INITIAL);
    }

    public Component(int id, String componentName, State state) {
        this.id = id;
        this.componentName = componentName;
        this.state = state;
        instances = new ArrayList<>();
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

    public void addInstance(Thread instance) {
        instances.add(instance);
    }

    public List<Thread> getAllInstances() {
        return instances;
    }

    public int getNextInstanceId() {
        return instances.size();
    }

    public void checkIsRunning() {
        if (instances.isEmpty()) {
            state = State.STOPPED;
        }
    }

}
