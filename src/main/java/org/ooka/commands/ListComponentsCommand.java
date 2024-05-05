package org.ooka.commands;

import org.ooka.runtime.Runtime;

public class ListComponentsCommand implements Command {

    @Override
    public boolean execute() {
        var runtime = Runtime.getInstance();

        if (runtime.getLoadedComponents().isEmpty()) {
            System.out.println("No components loaded");
        }
        else {
            for (var component : runtime.getLoadedComponents()) {
                System.out.println("\t" +component.getId() +"\t" + component.getComponentName() + " (" + component.getState().name() + ")");
            }
        }
        return true;
    }
}
