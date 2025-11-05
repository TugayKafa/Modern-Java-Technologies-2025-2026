package bg.sofia.uni.fmi.mjt.fittrack.workout.comparator;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

import java.util.Comparator;

public class WorkoutsDifficultyAscendingComparator implements Comparator<Workout> {
    @Override
    public int compare(Workout w1, Workout w2) {
        return w1.getDifficulty() - w2.getDifficulty();
    }
}
