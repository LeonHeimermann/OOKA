package org.ooka.component;

import org.ooka.types.State;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ComponentInstanceHandlerImpl implements ComponentInstanceHandler {
    private final Component component;
    private final List<Thread> instances;
    private final String THREAD_NAME_PREFIX = "Thread";

    public ComponentInstanceHandlerImpl(Component component) {
        this.component = component;
        instances = new CopyOnWriteArrayList<>();
    }

    public void checkIsRunning() {
        if (instances.isEmpty()) {
            component.setState(State.STOPPED);
        }
    }


    @Override
    public void startInstance() {
        ComponentRunnable componentRunnable = new ComponentRunnable(instances.size(), component);
        Thread thread = new Thread(componentRunnable, THREAD_NAME_PREFIX + instances.size());
        instances.add(thread);
        thread.start();
        component.setState(State.RUNNING);
    }

    @Override
    public void startInstances(int amount) {
        for (int i = 0; i < amount; i++) {
            startInstance();
        }
    }

    @Override
    public void removeInstanceById(int threadId, boolean flagInterrupt) {
        Thread thread = instances.stream()
                .filter(entry -> entry.getName().equals(THREAD_NAME_PREFIX + threadId))
                .findFirst()
                .orElse(null);
        if (thread == null) {
            return;
        }
        if (flagInterrupt) {
            thread.interrupt();
        }
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
