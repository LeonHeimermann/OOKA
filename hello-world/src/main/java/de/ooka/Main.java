package de.ooka;

import annotations.Inject;
import annotations.Start;
import annotations.Stop;
import org.ooka.logger.Logger;

public class Main {
    @Inject
    private static Logger logger;

    @Start
    public static void startMethod() {
        logger.info("Hello World");
    }

    @Stop
    public static void stopMethod() {
        logger.info("Stopping Hello World");
    }
}
