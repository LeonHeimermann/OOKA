package org.ooka.runtime;

import org.ooka.component.Component;

import java.util.List;

public interface Runtime {
    void addComponent(Component component);
    boolean removeComponent(int componentId);
    boolean startComponentInstance(int componentId);
    boolean startComponentInstances(int componentId, int amount);
    boolean stopComponentInstance(int componentId, int threadId);
    boolean stopAllComponentInstances(int componentId);
    String getStatus(int componentId);
    List<String> getAllStatus();
    List<Component> getDeployedComponents();
    void shutdown();
}
