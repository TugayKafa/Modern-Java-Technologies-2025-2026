package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public class DurationWorkoutFilter implements WorkoutFilter {
    private int minDuration;
    private int maxDuration;

    public DurationWorkoutFilter(int minDuration, int maxDuration) {
        setMinDuration(minDuration);
        setMaxDuration(maxDuration);
        if (this.minDuration > this.maxDuration) {
            throw new IllegalArgumentException("Max duration must be greather than min duration!");
        }
    }

    @Override
    public boolean matches(Workout workout) {
        int duration = workout.getDuration();
        return minDuration <= duration && duration <= maxDuration;
    }

    private void setMaxDuration(int maxDuration) {
        if (maxDuration < 0) {
            throw new IllegalArgumentException("Max duration is not negative!");
        }
        this.maxDuration = maxDuration;
    }

    private void setMinDuration(int minDuration) {
        if (minDuration < 0) {
            throw new IllegalArgumentException("Min duration is not negative!");
        }
        this.minDuration = minDuration;
    }
}