package org.ooka.component;

import org.ooka.types.State;

import java.util.ArrayList;
import java.util.List;

public class ComponentInstanceHandlerImpl implements ComponentInstanceHandler {
    private Component component;
    private List<Thread> instances;

    public ComponentInstanceHandlerImpl(Component component) {
        this.component = component;
        instances = new ArrayList<>();
    }

    public void checkIsRunning() {
        if (instances.isEmpty()) {
            component.setState(State.STOPPED);
        }
    }


    @Override
    public void startComponent() {
        ComponentRunnable componentRunnable = new ComponentRunnable(instances.size(), component);
        Thread thread = new Thread(componentRunnable);
        instances.add(thread);
        thread.start();
        component.setState(State.RUNNING);
    }

    @Override
    public void stopInstanceById(int threadId) {
        Thread thread = instances.stream()
                .filter(entry -> entry.getId() == threadId)
                .findFirst()
                .orElseThrow();
        thread.interrupt();
        instances.remove(thread);
        checkIsRunning();
    }

    @Override
    public void stopAllInstances() {
        instances.forEach(Thread::interrupt);
        instances.clear();
        component.setState(State.STOPPED);
    }
}
