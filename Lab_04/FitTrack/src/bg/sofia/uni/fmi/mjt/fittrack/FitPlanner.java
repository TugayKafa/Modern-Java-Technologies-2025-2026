package bg.sofia.uni.fmi.mjt.fittrack;

import bg.sofia.uni.fmi.mjt.fittrack.exception.OptimalPlanImpossibleException;
import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;
import bg.sofia.uni.fmi.mjt.fittrack.workout.WorkoutType;
import bg.sofia.uni.fmi.mjt.fittrack.workout.comparator.WorkoutsCaloriesDescendingComparator;
import bg.sofia.uni.fmi.mjt.fittrack.workout.comparator.WorkoutsDifficultyAscendingComparator;
import bg.sofia.uni.fmi.mjt.fittrack.workout.filter.WorkoutFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FitPlanner implements FitPlannerAPI {

    private List<Workout> availableWorkouts;

    FitPlanner(Collection<Workout> availableWorkouts) {
        this.availableWorkouts = new ArrayList<>();
        copyWorkouts(availableWorkouts);
    }

    @Override
    public List<Workout> findWorkoutsByFilters(List<WorkoutFilter> filters) {
        if (filters == null) {
            throw new IllegalArgumentException("Filters are null!");
        }

        return filterWorkouts(filters);
    }

    @Override
    public List<Workout> generateOptimalWeeklyPlan(int totalMinutes) throws OptimalPlanImpossibleException {
        if (totalMinutes < 0) {
            throw new IllegalArgumentException("Total minutes must not be negative!");
        }

        if (totalMinutes == 0 || availableWorkouts.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Integer, List<Workout>> workouts = new HashMap<>();
        for (int i = 0; i <= totalMinutes; i++) {
            workouts.putIfAbsent(i, new ArrayList<>());
        }

        int[] dp = new int[totalMinutes + 1];

        findOptimalPlan(totalMinutes, dp, workouts);

        if (dp[totalMinutes] == 0) {
            throw new OptimalPlanImpossibleException("Not optimal plan possible!");
        }

        List<Workout> optimalPlan = new ArrayList<>(workouts.get(totalMinutes));
        sortOptimalPlan(optimalPlan);

        return optimalPlan;
    }

    @Override
    public Map<WorkoutType, List<Workout>> getWorkoutsGroupedByType() {
        Map<WorkoutType, List<Workout>> groupedWorkouts = new HashMap<>();

        for (Workout workout : availableWorkouts) {
            WorkoutType type = workout.getType();
            groupedWorkouts.putIfAbsent(type, new ArrayList<>());
            groupedWorkouts.get(type).add(workout);
        }

        return Collections.unmodifiableMap(groupedWorkouts);
    }

    @Override
    public List<Workout> getWorkoutsSortedByCalories() {
        List<Workout> sortedWorkouts = new ArrayList<>(availableWorkouts);
        sortedWorkouts.sort(new WorkoutsCaloriesDescendingComparator());
        return sortedWorkouts;
    }

    @Override
    public List<Workout> getWorkoutsSortedByDifficulty() {
        List<Workout> sortedWorkouts = new ArrayList<>(availableWorkouts);
        sortedWorkouts.sort(new WorkoutsDifficultyAscendingComparator());
        return sortedWorkouts;
    }

    @Override
    public Set<Workout> getUnmodifiableWorkoutSet() {
        Set<Workout> workoutSet = new HashSet<>(availableWorkouts);
        return Collections.unmodifiableSet(workoutSet);
    }

    private void sortOptimalPlan(List<Workout> optimalPlan) {
        optimalPlan.sort(new WorkoutsCaloriesDescendingComparator()
                .thenComparing(new WorkoutsDifficultyAscendingComparator().reversed()));
    }

    private void findOptimalPlan(int totalMinutes, int[] dp, Map<Integer, List<Workout>> workouts) {
        for (Workout workout : availableWorkouts) {
            for (int j = totalMinutes; j >= workout.getDuration(); j--) {
                int newCalories = dp[j - workout.getDuration()] + workout.getCaloriesBurned();

                if (dp[j] < newCalories) {
                    dp[j] = newCalories;
                    workouts.put(j, new ArrayList<>(workouts.get(j - workout.getDuration())));
                    workouts.get(j).add(workout);
                }
            }
        }
    }

    private List<Workout> filterWorkouts(List<WorkoutFilter> filters) {
        List<Workout> workoutsByFilters = new ArrayList<>();

        for (Workout availableWorkout : availableWorkouts) {
            boolean isFiltered = true;
            for (WorkoutFilter filter : filters) {
                if (filter == null) {
                    continue;
                }
                if (!filter.matches(availableWorkout)) {
                    isFiltered = false;
                    break;
                }
            }
            if (isFiltered) {
                workoutsByFilters.add(availableWorkout);
            }
        }

        return workoutsByFilters;
    }

    private void copyWorkouts(Collection<Workout> availableWorkouts) {
        if (availableWorkouts == null) {
            throw new IllegalArgumentException("Available workouts must be not null!");
        }

        for (Workout availableWorkout : availableWorkouts) {
            if (availableWorkout == null) {
                continue;
            }
            this.availableWorkouts.add(availableWorkout);
        }
    }
}