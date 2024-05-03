package org.ooka.component;

public interface ComponentInstanceHandler {
    void startComponent();
    void stopInstanceById(int threadId);
    void stopAllInstances();
    void checkIsRunning();
}
