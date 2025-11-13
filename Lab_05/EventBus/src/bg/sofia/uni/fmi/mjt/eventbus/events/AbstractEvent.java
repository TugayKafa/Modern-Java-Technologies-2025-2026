package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public abstract class AbstractEvent<T extends Payload<?>> implements Event<T> {
    protected final String source;
    protected final int priority;
    protected final T payload;
    protected final Instant timestamp;

    protected AbstractEvent(String source, int priority, T payload, Instant timestamp) {
        this.source = source;
        this.priority = priority;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public T getPayload() {
        return payload;
    }
}
