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
    void init() {
        runtime = RuntimeImpl.getInstance();
        NoopClass.resetCounter();
    }

    @Test
    void runtimeRoundTripTest() throws InterruptedException, NoSuchMethodException {
        int componentId = 0;
        String componentName = "TestComponent";
        Component component = new Component(
                componentName,
                null,
                NoopClass.class.getDeclaredMethod("noopStart"),
                NoopClass.class.getDeclaredMethod("noopStop"),
                NoopClass.class
        );
        assertEquals(State.INITIAL, component.getState());

        runtime.addComponent(component);
        component.setId(componentId);
        assertEquals(State.LOADED, component.getState());
        assertEquals(0, NoopClass.getStartCounter());
        assertEquals(0, NoopClass.getStopCounter());

        for (int i = 0; i < 10; i++) {
            runtime.startComponentInstance(componentId);
        }
        assertEquals(State.RUNNING, component.getState());
        TimeUnit.SECONDS.sleep(1);
        assertTrue(NoopClass.getStartCounter() > 0);

        runtime.stopAllComponentInstances(componentId);
        TimeUnit.SECONDS.sleep(1);
        assertEquals(State.STOPPED, component.getState());
        assertTrue(NoopClass.getStopCounter() > 0);
    }

    @Test
    void testGetStatus() throws NoSuchMethodException {
        int componentId = 0;
        String componentName = "TestComponent";
        Component component = new Component(
                componentName,
                null,
                NoopClass.class.getDeclaredMethod("noopStart"),
                NoopClass.class.getDeclaredMethod("noopStop"),
                NoopClass.class
        );
        runtime.addComponent(component);
        component.setId(componentId);
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
    void testGetAllStatus() throws NoSuchMethodException {
        String componentNameTmp = "Component_%s";
        List<String> expectedStatus = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String componentName = String.format(componentNameTmp, i);
            expectedStatus.add(generateStatus(i, componentName, State.RUNNING));
            Component newComponent = new Component(
                    componentName,
                    null,
                    NoopClass.class.getDeclaredMethod("noopStart"),
                    NoopClass.class.getDeclaredMethod("noopStop"),
                    NoopClass.class
            );
            runtime.addComponent(newComponent);
            newComponent.setId(i);
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
