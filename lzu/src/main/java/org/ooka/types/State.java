package org.ooka.types;

public enum State {
    INITIAL("initial"),
    LOADED("loaded"),
    RUNNING("running"),
    PAUSED("paused"),
    STOPPED("stopped");

    private final String label;

    State(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
