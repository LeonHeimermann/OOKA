package org.ooka.logger;

import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LoggerImpl implements Logger {
    private static LoggerImpl logger;
    private PrintStream infoPrintStream;
    private PrintStream errorPrintStream;

    public static LoggerImpl getInstance() {
        if (logger == null) {
            logger = new LoggerImpl();
        }
        return logger;
    }

    private void checkInfoPrintStreamInitialized() {
        if (infoPrintStream == null) {
            infoPrintStream = new PrintStream(System.out);
        }
    }

    private void checkErrorPrintStreamInitialized() {
        if (errorPrintStream == null) {
            errorPrintStream = new PrintStream(System.err);
        }
    }

    public void info(String message) {
        checkInfoPrintStreamInitialized();
        log(message, infoPrintStream);
    }

    public void error(String message) {
        checkErrorPrintStreamInitialized();
        log(message, errorPrintStream);
    }

    private void log(String message, PrintStream printStream) {
        LocalTime localTime = LocalTime.now();
        String timeFormatted = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String outputMessage = String.format("[%s] %s", timeFormatted, message);
        printStream.println(outputMessage);
    }

}
