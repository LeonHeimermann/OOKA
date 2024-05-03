package org.ooka.runtime;

import org.ooka.component.Component;
import org.ooka.types.State;

import java.util.*;

public class RuntimeImpl implements Runtime {
    private static Runtime runtime = null;
    private List<Component> loadedComponents = new ArrayList<>();

    public static Runtime getInstance() {
        if(runtime == null){
            runtime = new RuntimeImpl();
        }
        return runtime;
    }

    @Override
    public void addComponent(Component component) {
        loadedComponents.add(component);
        component.setState(State.LOADED);
    }

    @Override
    public boolean startComponentInstance(int componentId) {
        try {
            Component component = getComponentById(componentId);
            component.startComponent();
            return true;
        } catch (NoSuchElementException e) {
            System.out.printf("No component found with id: %s%n", componentId);
            return false;
        } catch (RuntimeException e) {
            System.out.printf("An error occurred while starting component %s%n", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean stopComponentInstance(int componentId, int threadId) {
        try {
            Component component = getComponentById(componentId);
            component.stopInstanceById(threadId);
            return true;
        } catch (NoSuchElementException e) {
            System.out.printf("No component found with id: %s", componentId);
            return false;
        } catch (RuntimeException e) {
            System.out.printf("An error occurred while stopping component %s%n", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean stopAllComponentInstances(int componentId) {
        try {
            Component component = getComponentById(componentId);
            component.stopAllInstances();
            return true;
        } catch (NoSuchElementException e) {
            System.out.printf("No component found with id: %s", componentId);
            return false;
        } catch (RuntimeException e) {
            System.out.printf("An error occurred while stopping component %s%n", e.getMessage());
            return false;
        }
    }

    @Override
    public String getStatus(int componentId) {
        try {
            Component component = getComponentById(componentId);
            return component.toString();
        } catch (NoSuchElementException e) {
            System.out.printf("No component found with id: %s", componentId);
            return null;
        } catch (RuntimeException e) {
            System.out.printf("An error occurred while stopping component %s%n", e.getMessage());
            return null;
        }
    }

    @Override
    public List<String> getAllStatus() {
        return loadedComponents.stream()
                .map(Component::toString)
                .toList();
    }

    @Override
    public void shutdown() {
        loadedComponents.forEach(entry -> stopAllComponentInstances(entry.getId()));
        loadedComponents.clear();
    }

    private Component getComponentById(int componentId) {
        return loadedComponents.stream()
                .filter(entry -> entry.getId() == componentId)
                .findFirst()
                .orElseThrow();
    }

}
