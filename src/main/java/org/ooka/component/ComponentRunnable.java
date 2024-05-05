package org.ooka.component;

import java.lang.reflect.InvocationTargetException;

public class ComponentRunnable implements Runnable {

    private final int id;
    private final Component component;

    public ComponentRunnable(int id, Component component) {
        this.id = id;
        this.component = component;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            var instance = component.getStartingClass().getDeclaredConstructor().newInstance();
            try {
                component.getStartMethod().invoke(instance);
            } catch (InvocationTargetException e) {
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                component.getStopMethod().invoke(instance);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        component.removeInstanceById(id, false);
    }
}
