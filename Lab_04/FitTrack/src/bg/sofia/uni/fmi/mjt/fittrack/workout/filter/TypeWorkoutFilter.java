package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;
import bg.sofia.uni.fmi.mjt.fittrack.workout.WorkoutType;

public class TypeWorkoutFilter implements WorkoutFilter {
    private WorkoutType type;

    public TypeWorkoutFilter(WorkoutType type) {
        setType(type);
    }

    private void setType(WorkoutType type) {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null!");
        }
        this.type = type;
    }

    @Override
    public boolean matches(Workout workout) {
        return type.name().equals(workout.getType().name());
    }
}
