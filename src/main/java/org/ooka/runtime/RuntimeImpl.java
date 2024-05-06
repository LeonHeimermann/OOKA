package org.ooka.runtime;

import org.ooka.component.Component;
import org.ooka.types.State;

import java.util.*;

public class RuntimeImpl implements Runtime {
    private static Runtime runtime = null;
    private final List<Component> loadedComponents;

    public static Runtime getInstance() {
        if (runtime == null) {
            runtime = new RuntimeImpl();
        }
        return runtime;
    }

    public RuntimeImpl() {
        loadedComponents = new ArrayList<>();
    }

    @Override
    public void addComponent(Component component) {
        component.setId(loadedComponents.size());
        loadedComponents.add(component);
        component.setState(State.LOADED);
    }

    @Override
    public boolean removeComponent(int componentId) {
        try {
            Component component = getComponentById(componentId);
            stopAllComponentInstances(componentId);
            loadedComponents.remove(component);
            return true;
        } catch (NoSuchElementException e) {
            handleNoSuchElementException(componentId);
            return false;
        } catch (RuntimeException e) {
            handleRuntimeException(e);
            return false;
        }
    }

    @Override
    public boolean startComponentInstance(int componentId) {
        try {
            Component component = getComponentById(componentId);
            component.startInstance();
            return true;
        } catch (NoSuchElementException e) {
            handleNoSuchElementException(componentId);
            return false;
        } catch (RuntimeException e) {
            handleRuntimeException(e);
            return false;
        }
    }

    @Override
    public boolean startComponentInstances(int componentId, int amount) {
        try {
            Component component = getComponentById(componentId);
            component.startInstances(amount);
            return true;
        } catch (NoSuchElementException e) {
            handleNoSuchElementException(componentId);
            return false;
        } catch (RuntimeException e) {
            handleRuntimeException(e);
            return false;
        }
    }

    @Override
    public boolean stopComponentInstance(int componentId, int threadId) {
        try {
            Component component = getComponentById(componentId);
            component.removeInstanceById(threadId, true);
            return true;
        } catch (NoSuchElementException e) {
            handleNoSuchElementException(componentId);
            return false;
        } catch (RuntimeException e) {
            handleRuntimeException(e);
            return false;
        }
    }

    @Override
    public boolean stopAllComponentInstances(int componentId) {
        try {
            Component component = getComponentById(componentId);
            stopAllComponentInstances(component);
            return true;
        } catch (NoSuchElementException e) {
            handleNoSuchElementException(componentId);
            return false;
        } catch (RuntimeException e) {
            handleRuntimeException(e);
            return false;
        }
    }

    private void stopAllComponentInstances(Component component) {
            component.stopAllInstances();
    }

    @Override
    public String getStatus(int componentId) {
        try {
            Component component = getComponentById(componentId);
            return component.toString();
        } catch (NoSuchElementException e) {
            handleNoSuchElementException(componentId);
            return null;
        } catch (RuntimeException e) {
            handleRuntimeException(e);
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
    public List<Component> getDeployedComponents() {
        return loadedComponents;
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

    private void handleNoSuchElementException(int componentId) {
        System.out.printf("No component found with id: %s\n", componentId);
    }

    private void handleRuntimeException(RuntimeException e) {
        System.out.printf("An error occurred while stopping component %s%n", e.getMessage());
    }
}
