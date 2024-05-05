package org.ooka.component;

public interface ComponentInstanceHandler {
    void startInstance();
    void startInstances(int amount);
    void removeInstanceById(int threadId, boolean flagInterrupt);
    void stopAllInstances();
    void checkIsRunning();
}
