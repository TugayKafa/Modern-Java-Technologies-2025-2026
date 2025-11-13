package bg.sofia.uni.fmi.mjt.eventbus.events;

public class StringPayload extends AbstractPayload<String> {

    public StringPayload(String payload) {
        super(payload);
    }

    @Override
    public int getSize() {
        return getPayload().length();
    }
}
