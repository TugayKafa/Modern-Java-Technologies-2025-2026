package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class HumorousErgenka extends AbstractErgenka {

    private static final int BONUS_FOR_NORMAL_DURATION = 4;
    private static final int SHORT_DATE_DURATION = 30;
    private static final int BONUS_FOR_SHORT_DATE = 2;
    private static final int LONG_DATE_DURATION = 120;
    private static final int BONUS_FOR_LONG_DATE = 3;
    private static final int ROMANCE_LEVEL_DIVIDER = 3;
    private static final int HUMOR_LEVEL_MULTIPLIER = 5;
    private static final int NORMAL_DURATION = 90;

    private final DateEvent defaultDateEvent = new DateEvent("default", 5, 60);

    public HumorousErgenka(String name,
                           short age,
                           int romanceLevel,
                           int humorLevel,
                           int rating) {
        super(name, age, romanceLevel, humorLevel, rating);
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        if (dateEvent == null) {
            dateEvent = defaultDateEvent;
        }

        int delta = (humorLevel * HUMOR_LEVEL_MULTIPLIER) / dateEvent.getTensionLevel() +
                Math.floorDiv(romanceLevel, ROMANCE_LEVEL_DIVIDER);

        int duration = dateEvent.getDuration();
        if (duration < SHORT_DATE_DURATION) {
            delta -= BONUS_FOR_SHORT_DATE;
        } else if (duration > LONG_DATE_DURATION) {
            delta -= BONUS_FOR_LONG_DATE;
        } else if (duration <= NORMAL_DURATION) {
            delta += BONUS_FOR_NORMAL_DURATION;
        }

        rating += delta;
    }
}
