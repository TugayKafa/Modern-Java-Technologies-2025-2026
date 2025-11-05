package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public class NameWorkoutFilter implements WorkoutFilter {
    private String keyword;
    private boolean caseSensitive;

    public NameWorkoutFilter(String keyword, boolean caseSensitive) {
        setKeyword(keyword);
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean matches(Workout workout) {
        String name = caseSensitive ? workout.getName() : workout.getName().toLowerCase();
        String key = caseSensitive ? keyword : keyword.toLowerCase();

        return name.equals(key) || name.contains(key);
    }

    private void setKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword must not be blank!");
        }
        this.keyword = keyword;
    }
}