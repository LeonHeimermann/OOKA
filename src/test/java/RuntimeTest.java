import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ooka.component.Component;
import org.ooka.runtime.Runtime;
import org.ooka.types.State;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuntimeTest {

    private Runtime runtime;

    @BeforeEach
    void inti() {
        runtime = Runtime.getInstance();
    }

    @Test
    void runtimeRoundTripTest() throws InterruptedException {
        int componentId = 0;
        String componentName = "TestComponent";
        Component component = new Component(componentId, componentName);
        assertEquals(State.INITIAL, component.getState());
        runtime.addComponent(component);
        assertEquals(State.LOADED, component.getState());
        assertEquals(0, component.getAllInstances().size());
        for (int i = 0; i < 10; i++) {
            runtime.startComponent(componentId);
            assertEquals(i + 1, component.getAllInstances().size());
        }
        assertEquals(State.RUNNING, component.getState());
        TimeUnit.SECONDS.sleep(10);
        System.out.println("stopping");
        runtime.stopAllComponents(componentId);
        System.out.println("stopped");
        TimeUnit.SECONDS.sleep(10);
        assertEquals(State.STOPPED, component.getState());
        assertEquals(0, component.getAllInstances().size());
    }
}
