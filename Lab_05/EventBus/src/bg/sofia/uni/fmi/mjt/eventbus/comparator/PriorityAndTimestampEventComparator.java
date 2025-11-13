package bg.sofia.uni.fmi.mjt.eventbus.comparator;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

import java.util.Comparator;

public class PriorityAndTimestampEventComparator implements Comparator<Event<?>> {
    @Override
    public int compare(Event<?> event1, Event<?> event2) {
        int priorityDiff = event1.getPriority() - event2.getPriority();
        if (priorityDiff == 0) {
            return event1.getTimestamp().compareTo(event2.getTimestamp());
        }

        return priorityDiff;
    }
}
