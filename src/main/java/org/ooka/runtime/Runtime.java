package org.ooka.runtime;

import org.ooka.component.Component;

import java.util.List;

public interface Runtime {
    void addComponent(Component component);
    boolean removeComponent(int componentId);
    boolean startComponentInstance(int componentId);
    boolean stopComponentInstance(int componentId, int threadId);
    boolean stopAllComponentInstances(int componentId);
    String getStatus(int componentId);
    List<String> getAllStatus();
    void shutdown();
}
