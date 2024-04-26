package org.ooka.component;

public class ComponentRunnable implements Runnable{

    private int id;

    private Component component;

    public  ComponentRunnable(Component component) {
        this(component.getNextInstanceId(), component);
    }

    public ComponentRunnable(int id, Component component) {
        this.id = id;
        this.component = component;
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

    public int getId() {
        return id;
    }

    public Component getComponent() {
        return component;
    }
}
