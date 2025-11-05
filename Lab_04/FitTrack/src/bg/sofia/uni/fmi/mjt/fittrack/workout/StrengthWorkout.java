package bg.sofia.uni.fmi.mjt.fittrack.workout;

public final class StrengthWorkout extends BaseWorkout implements Workout {

    public StrengthWorkout(String name, int duration, int caloriesBurned, int difficulty) {
        super(name, duration, caloriesBurned, difficulty, WorkoutType.STRENGTH);
    }
}
