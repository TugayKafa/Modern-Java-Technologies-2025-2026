package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import bg.sofia.uni.fmi.mjt.eventbus.events.StringEvent;

public class StringEventSubscriber implements Subscriber<StringEvent> {

    @Override
    public void onEvent(StringEvent event) {
        System.out.println("source: " + event.getSource() + "\nmessage: " + event.getPayload().getPayload());
    }
}
