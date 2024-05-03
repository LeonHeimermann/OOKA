package org.ooka.component;

public class ComponentRunnable implements Runnable{

    private int id;

    private Component component;

    public ComponentRunnable(int id, Component component) {
        this.id = id;
        this.component = component;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted()) {
                break;
            }
        }
        component.checkIsRunning();
    }
}
