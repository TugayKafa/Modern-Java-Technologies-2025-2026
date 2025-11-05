package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public class CaloriesWorkoutFilter implements WorkoutFilter {
    private int minCalories;
    private int maxCalories;

    public CaloriesWorkoutFilter(int minCalories, int maxCalories) {
        setMinCalories(minCalories);
        setMaxCalories(maxCalories);
        if (this.minCalories > this.maxCalories) {
            throw new IllegalArgumentException("Max calories must be greather than min calories!");
        }
    }

    @Override
    public boolean matches(Workout workout) {
        int caloriesBurned = workout.getCaloriesBurned();
        return minCalories <= caloriesBurned && caloriesBurned <= maxCalories;
    }

    private void setMaxCalories(int maxCalories) {
        if (maxCalories < 0) {
            throw new IllegalArgumentException("Max calories is not negative!");
        }
        this.maxCalories = maxCalories;
    }

    private void setMinCalories(int minCalories) {
        if (minCalories < 0) {
            throw new IllegalArgumentException("Min calories is not negative!");
        }
        this.minCalories = minCalories;
    }
}
