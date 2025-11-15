package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public abstract class AbstractErgenka implements Ergenka {

    protected String name = "default";
    protected short age;
    protected int romanceLevel;
    protected int humorLevel;
    protected int rating;

    public AbstractErgenka(String name, short age, int romanceLevel, int humorLevel, int rating) {
        if (name != null) {
            this.name = name;
        }
        this.age = age;
        this.romanceLevel = romanceLevel;
        this.humorLevel = humorLevel;
        this.rating = rating;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public short getAge() {
        return age;
    }

    @Override
    public int getRomanceLevel() {
        return romanceLevel;
    }

    @Override
    public int getHumorLevel() {
        return humorLevel;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public abstract void reactToDate(DateEvent dateEvent);
}
