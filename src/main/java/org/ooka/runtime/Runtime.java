package org.ooka.runtime;

import org.ooka.component.Component;
import org.ooka.component.ComponentRunnable;
import org.ooka.types.State;

import java.util.*;

public class Runtime {
    private static Runtime runtime = null;
    private List<Component> loadedComponents = new ArrayList<>();

    public static Runtime getInstance(){
        if(runtime == null){
            runtime = new Runtime();
        }
        return runtime;
    }

    public void addComponent(Component component) {
        loadedComponents.add(component);
        component.setState(State.LOADED);
    }

    public boolean startComponent(int componentId) {
        try {
            Component component = getComponentById(componentId);
            ComponentRunnable componentRunnable = new ComponentRunnable(component);
            Thread thread = new Thread(componentRunnable);
            component.addInstance(thread);
            thread.start();
            component.setState(State.RUNNING);
            return true;
        } catch (NoSuchElementException e) {
            System.out.printf("No component found with id: %s%n", componentId);
            return false;
        } catch (RuntimeException e) {
            System.out.printf("An error occurred while starting component %s%n", e.getMessage());
            return false;
        }
    }

    public boolean stopComponent(int componentId, int threadId) {
        try {
            Component component = getComponentById(componentId);
            List<Thread> instances = component.getAllInstances();
            Thread thread = component.getAllInstances().stream()
                    .filter(entry -> entry.getId() == threadId)
                    .findFirst()
                    .orElseThrow();
            thread.interrupt();
            instances.remove(thread);
            component.checkIsRunning();
            return true;
        } catch (NoSuchElementException e) {
            System.out.printf("No component found with id: %s", componentId);
            return false;
        } catch (RuntimeException e) {
            System.out.printf("An error occurred while stopping component %s%n", e.getMessage());
            return false;
        }
    }

    public boolean stopAllComponents(int componentId) {
        try {
            Component component = getComponentById(componentId);
            List<Thread> instances = component.getAllInstances();
            instances.forEach(Thread::interrupt);
            instances.clear();
            component.setState(State.STOPPED);
            return true;
        } catch (NoSuchElementException e) {
            System.out.printf("No component found with id: %s", componentId);
            return false;
        } catch (RuntimeException e) {
            System.out.printf("An error occurred while stopping component %s%n", e.getMessage());
            return false;
        }
    }

    public List<Component> getLoadedComponents() {
        return loadedComponents;
    }

    private Component getComponentById(int componentId) {
        return loadedComponents.stream()
                .filter(entry -> entry.getId() == componentId)
                .findFirst()
                .orElseThrow();
    }
}
