import java.util.concurrent.TimeUnit;

public class NoopClass {

    private static int startCounter = 0;
    private static int stopCounter = 0;

    public static int getStartCounter() {
        return startCounter;
    }

    public static int getStopCounter() {
        return stopCounter;
    }

    public static void resetCounter() {
        startCounter = 0;
        stopCounter = 0;
    }

    public void noopStart() throws InterruptedException {
        startCounter += 1;
        TimeUnit.SECONDS.sleep(10);
    }

    public void noopStop() {
        stopCounter += 1;
    }
}
