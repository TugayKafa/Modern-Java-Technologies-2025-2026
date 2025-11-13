package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EventBusImpl implements EventBus {

    private HashMap<Class<? extends Event<?>>, List<Subscriber<?>>> subscribers;
    private HashMap<Class<? extends Event<?>>, List<Event<?>>> events;

    public EventBusImpl() {
        subscribers = new HashMap<>();
        events = new HashMap<>();
    }

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        if (eventType == null) {
            throw new IllegalArgumentException("eventType is null!");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber is null!");
        }

        subscribers.putIfAbsent(eventType, new ArrayList<>());
        List<Subscriber<?>> subscribersByType = subscribers.get(eventType);
        for (Subscriber<?> subscriberByType : subscribersByType) {
            if (subscriberByType == subscriber) {
                return;
            }
        }
        subscribers.get(eventType).add(subscriber);
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
            throws MissingSubscriptionException {
        if (eventType == null) {
            throw new IllegalArgumentException("eventType is null!");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber is null!");
        }

        List<Subscriber<?>> subscribersByType = subscribers.get(eventType);
        if (subscribersByType == null) {
            throw new MissingSubscriptionException("Subscriber is not subscribed for event: " +
                    eventType.getTypeName());
        }

        if (!subscribersByType.remove(subscriber)) {
            throw new MissingSubscriptionException("Subscriber is not subscribed for event: " +
                    eventType.getTypeName());
        }
    }

    @Override
    public <T extends Event<?>> void publish(T event) {
        if (event == null) {
            throw new IllegalArgumentException("eventType is null!");
        }

        List<Subscriber<?>> subscribersByType = subscribers.get(event.getClass());
        if (subscribersByType == null) {
            return;
        }

        Class<? extends Event<?>> clazz = (Class<? extends Event<?>>) event.getClass();
        // We now that T extends Event<?>
        events.putIfAbsent(clazz, new ArrayList<>());
        events.get(clazz).add(event);

        for (Subscriber<?> subscriber : subscribersByType) {
            // We now that subscriber is exactly from the type that we want
            Subscriber<T> sub = (Subscriber<T>) subscriber;
            sub.onEvent(event);
        }
    }

    @Override
    public void clear() {
        subscribers.clear();
        events.clear();
    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        if (eventType == null) {
            throw new IllegalArgumentException("eventType is null!");
        }
        if (from == null) {
            throw new IllegalArgumentException("Instant variable from is null!");
        }
        if (to == null) {
            throw new IllegalArgumentException("Instant variable to is null!");
        }

        events.putIfAbsent(eventType, new ArrayList<>());
        List<Event<?>> eventsByType = events.get(eventType);

        List<Event<?>> res = new ArrayList<>();
        for (Event<?> event : eventsByType) {
            Instant timestamp = event.getTimestamp();
            if (timestamp.isAfter(from) && timestamp.isBefore(to)) {
                res.add(event);
            }
        }

        return Collections.unmodifiableCollection(res);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        List<Subscriber<?>> subscribersByType;
        if (!subscribers.containsKey(eventType)) {
            subscribersByType = new ArrayList<>();
        } else {
            subscribersByType = subscribers.get(eventType);
        }

        return Collections.unmodifiableCollection(subscribersByType);
    }
}
