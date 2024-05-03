import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ooka.component.Component;
import org.ooka.runtime.Runtime;
import org.ooka.runtime.RuntimeImpl;
import org.ooka.types.State;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class RuntimeTest {

    private Runtime runtime;

    @BeforeEach
    void inti() {
        runtime = RuntimeImpl.getInstance();
    }

    @Test
    void runtimeRoundTripTest() throws InterruptedException {
        int componentId = 0;
        String componentName = "TestComponent";
        Component component = new Component(componentId, componentName);
        assertEquals(State.INITIAL, component.getState());
        runtime.addComponent(component);
        assertEquals(State.LOADED, component.getState());
        for (int i = 0; i < 10; i++) {
            runtime.startComponentInstance(componentId);
        }
        assertEquals(State.RUNNING, component.getState());
        TimeUnit.SECONDS.sleep(10);
        runtime.stopAllComponentInstances(componentId);
        TimeUnit.SECONDS.sleep(10);
        assertEquals(State.STOPPED, component.getState());
    }

    @Test
    void testGetStatus() {
        int componentId = 0;
        String componentName = "TestComponent";
        Component component = new Component(componentId, componentName);
        runtime.addComponent(component);
        String statusLoaded = runtime.getStatus(componentId);
        String expectedStatusLoaded = generateStatus(componentId, componentName, State.LOADED);
        assertEquals(statusLoaded, expectedStatusLoaded);
        runtime.startComponentInstance(componentId);
        String statusRunning = runtime.getStatus(componentId);
        String expectedStatusRunning = generateStatus(componentId, componentName, State.RUNNING);
        assertEquals(statusRunning, expectedStatusRunning);
        runtime.stopAllComponentInstances(componentId);
        String statusStopped = runtime.getStatus(componentId);
        String expectedStatusStopped = generateStatus(componentId, componentName, State.STOPPED);
        assertEquals(statusStopped, expectedStatusStopped);
    }

    @Test
    void testGetAllStatus() {
        String componentNameTmp = "Component_%s";
        List<String> expectedStatus = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String componentName = String.format(componentNameTmp, i);
            expectedStatus.add(generateStatus(i, componentName, State.RUNNING));
            runtime.addComponent(new Component(i, componentName));
            runtime.startComponentInstance(i);
        }
        List<String> statusList = runtime.getAllStatus();
        assertEquals(10, statusList.size());
        assertEquals(statusList, expectedStatus);
    }

    @AfterEach
    void cleanUp() {
        runtime.shutdown();
    }

    private String generateStatus(int componentId, String componentName, State state) {
        return String.format("id: %s, name: %s, state: %s", componentId, componentName, state);
    }
}
