import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ooka.commands.*;
import org.ooka.component.Component;
import org.ooka.runtime.Runtime;
import org.ooka.runtime.RuntimeImpl;
import org.ooka.types.State;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    private Runtime runtime;

    @BeforeEach
    void init() {
        runtime = RuntimeImpl.getInstance();
        NoopClass.resetCounter();
    }

    @Test
    void testDeployStartStopCommand() throws NoSuchMethodException, InterruptedException {
        int componentId = 0;
        String componentName = "testComponent";
        Component testComponent = new Component(
                componentName,
                NoopClass.class.getDeclaredMethod("noopStart"),
                NoopClass.class.getDeclaredMethod("noopStop"),
                NoopClass.class
        );
        assertEquals(0, NoopClass.getStartCounter());
        assertEquals(0, NoopClass.getStopCounter());
        assertEquals(State.INITIAL, testComponent.getState());
        runtime.addComponent(testComponent);
        testComponent.setId(componentId);
        assertEquals(State.LOADED, testComponent.getState());

        Command startInstanceCommand = new StartInstanceCommand(runtime, componentId);
        startInstanceCommand.execute();
        assertEquals(State.RUNNING, testComponent.getState());
        TimeUnit.SECONDS.sleep(1);
        assertEquals(1, NoopClass.getStartCounter());

        Command stopAllInstancesCommand = new StopAllInstancesCommand(runtime, componentId);
        stopAllInstancesCommand.execute();
        TimeUnit.SECONDS.sleep(1);
        assertEquals(State.STOPPED, testComponent.getState());
        assertEquals(1, NoopClass.getStopCounter());

        RemoveComponentCommand removeComponentCommand = new RemoveComponentCommand(runtime, componentId);
        removeComponentCommand.execute();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expectedOutput = String.format("No component found with id: %s", componentId);
        runtime.getStatus(componentId);
        assertEquals(expectedOutput, outContent.toString().trim());
        System.setOut(System.out);
    }

    @Test
    void testPrintStatusCommand() throws NoSuchMethodException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        int componentId = 0;
        String componentName = "testComponent";
        Component testComponent = new Component(
                componentName,
                NoopClass.class.getDeclaredMethod("noopStart"),
                NoopClass.class.getDeclaredMethod("noopStop"),
                NoopClass.class
        );
        runtime.addComponent(testComponent);
        testComponent.setId(componentId);
        Command printStatusCommand = new PrintStatusCommand(runtime, componentId);
        printStatusCommand.execute();
        String expectedOutput = String.format("id: %s, name: %s, state: loaded", componentId, componentName);
        assertEquals(expectedOutput, outContent.toString().trim());
        System.setOut(System.out);
    }

    @Test
    void testPrintAllStatusCommand() throws NoSuchMethodException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        int componentId = 0;
        String componentName = "testComponent";
        Component testComponent = new Component(
                componentName,
                NoopClass.class.getDeclaredMethod("noopStart"),
                NoopClass.class.getDeclaredMethod("noopStop"),
                NoopClass.class
        );
        runtime.addComponent(testComponent);
        testComponent.setId(componentId);
        Command printAllStatusCommand = new PrintAllStatusCommand(runtime);
        printAllStatusCommand.execute();
        String expectedOutput = String.format("id: %s, name: %s, state: loaded", componentId, componentName);
        assertEquals(expectedOutput, outContent.toString().trim());
        System.setOut(System.out);
    }



    @AfterEach
    void cleanUp() {
        runtime.shutdown();
    }

}
