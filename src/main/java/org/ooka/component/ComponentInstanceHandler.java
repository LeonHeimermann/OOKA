package org.ooka.component;

public interface ComponentInstanceHandler {
    void startInstance();
    void startInstances(int amount);
    void stopInstanceById(int threadId);
    void stopAllInstances();
    void checkIsRunning();
}
