package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class RomanticErgenka extends AbstractErgenka {

    private static final int BONUS_FOR_FAVOURITE_PLACE = 5;
    private static final int SHORT_DATE_DURATION = 30;
    private static final int BONUS_FOR_SHORT_DATE = 3;
    private static final int LONG_DATE_DURATION = 120;
    private static final int BONUS_FOR_LONG_DATE = 2;
    private static final int HUMOR_LEVEL_DIVIDER = 3;
    private static final int ROMANCE_LEVEL_MULTIPLIER = 7;

    private String favoriteDateLocation;

    public RomanticErgenka(String name, short age, int romanceLevel, int humorLevel, int rating, String favoriteDateLocation) {
        super(name, age, romanceLevel, humorLevel, rating);
        this.favoriteDateLocation = favoriteDateLocation;
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        rating = (romanceLevel * ROMANCE_LEVEL_MULTIPLIER) / dateEvent.getTensionLevel() + humorLevel / HUMOR_LEVEL_DIVIDER;

        if (favoriteDateLocation.equalsIgnoreCase(dateEvent.getLocation())) {
            rating += BONUS_FOR_FAVOURITE_PLACE;
        }

        int duration = dateEvent.getDuration();
        if (duration < SHORT_DATE_DURATION) {
            rating -= BONUS_FOR_SHORT_DATE;
        } else if (duration > LONG_DATE_DURATION) {
            rating -= BONUS_FOR_LONG_DATE;
        }
    }
}
