package org.ooka.component;

import org.ooka.types.State;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Component {

    private int id;
    private String componentName;
    private State state;
    private List<Thread> instances;
    final Method startMethod;
    final Method stopMethod;
    final Class startingClass;

    public Component(int id, String componentName, Class startingClass, Method startMethod, Method stopMethod) {
        this(id, componentName, State.INITIAL, startingClass, startMethod, stopMethod);
    }

    public Component(int id, String componentName, State state, Class startingClass, Method startMethod, Method stopMethod) {
        this.id = id;
        this.componentName = componentName;
        this.state = state;
        this.startingClass = startingClass;
        this.startMethod = startMethod;
        this.stopMethod = stopMethod;
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
