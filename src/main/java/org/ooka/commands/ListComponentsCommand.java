package org.ooka.commands;

import org.ooka.runtime.RuntimeImpl;

public class ListComponentsCommand implements Command {

    @Override
    public boolean execute() {
        var runtime = RuntimeImpl.getInstance();

        if (runtime.getDeployedComponents().isEmpty()) {
            System.out.println("No components loaded");
        }
        else {
            for (var component : runtime.getDeployedComponents()) {
                System.out.println("\t" +component.getId() +"\t" + component.getComponentName() + " (" + component.getState().name() + ")");
            }
        }
        return true;
    }
}
