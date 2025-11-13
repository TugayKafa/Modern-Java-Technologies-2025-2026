package bg.sofia.uni.fmi.mjt.eventbus.events;

public abstract class AbstractPayload<T> implements Payload<T> {
    protected final T payload;

    public AbstractPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public abstract int getSize();

    @Override
    public T getPayload() {
        return payload;
    }
}
