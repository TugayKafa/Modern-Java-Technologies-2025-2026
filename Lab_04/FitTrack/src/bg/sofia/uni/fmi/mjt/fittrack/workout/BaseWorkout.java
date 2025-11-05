package bg.sofia.uni.fmi.mjt.fittrack.workout;

import bg.sofia.uni.fmi.mjt.fittrack.exception.InvalidWorkoutException;

import java.util.Objects;

public abstract class BaseWorkout {
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;
    private final WorkoutType type;
    private String name;
    private int duration;
    private int caloriesBurned;
    private int difficulty;

    public BaseWorkout(String name, int duration, int caloriesBurned, int difficulty, WorkoutType type) {
        setName(name);
        setDuration(duration);
        setCaloriesBurned(caloriesBurned);
        setDifficulty(difficulty);
        this.type = type;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidWorkoutException("Workout name is blank!");
        }
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    private void setDuration(int duration) {
        if (duration <= 0) {
            throw new InvalidWorkoutException("Workout duration must be positive!");
        }
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    private void setCaloriesBurned(int caloriesBurned) {
        if (caloriesBurned <= 0) {
            throw new InvalidWorkoutException("Calories burned must be positive!");
        }
        this.caloriesBurned = caloriesBurned;
    }

    public int getDifficulty() {
        return difficulty;
    }

    private void setDifficulty(int difficulty) {
        if (difficulty < MIN_RATING || difficulty > MAX_RATING) {
            throw new InvalidWorkoutException("Workout difficulty must be between 1-5!");
        }
        this.difficulty = difficulty;
    }

    public WorkoutType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BaseWorkout that = (BaseWorkout) obj;
        return duration == that.duration &&
                Objects.equals(name, that.name) &&
                type == that.type;
    }

    @Override
    public String toString() {
        return "BaseWorkout{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", caloriesBurned=" + caloriesBurned +
                ", difficulty=" + difficulty +
                '}';
    }
}