package bg.sofia.uni.fmi.mjt.fittrack.workout;

public final class YogaSession extends BaseWorkout implements Workout {

    public YogaSession(String name, int duration, int caloriesBurned, int difficulty) {
        super(name, duration, caloriesBurned, difficulty, WorkoutType.YOGA);
    }
}
