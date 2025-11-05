package bg.sofia.uni.fmi.mjt.fittrack.workout.comparator;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

import java.util.Comparator;

public class WorkoutsCaloriesDescendingComparator implements Comparator<Workout> {
    @Override
    public int compare(Workout w1, Workout w2) {
        return w2.getCaloriesBurned() - w1.getCaloriesBurned();
    }
}
