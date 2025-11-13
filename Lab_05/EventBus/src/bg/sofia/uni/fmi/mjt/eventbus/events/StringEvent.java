package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public class StringEvent extends AbstractEvent<StringPayload> {

    public StringEvent(String source, int priority, StringPayload payload, Instant timestamp) {
        super(source, priority, payload, timestamp);
    }
}