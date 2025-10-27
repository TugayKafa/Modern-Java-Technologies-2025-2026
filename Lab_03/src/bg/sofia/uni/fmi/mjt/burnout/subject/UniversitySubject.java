package bg.sofia.uni.fmi.mjt.burnout.subject;

/**
 * @param name            the name of the subject
 * @param credits         number of credit hours for this subject
 * @param rating          difficulty rating of the subject (1-5)
 * @param category        the academic category this subject belongs to
 * @param neededStudyTime estimated study time in days required for this subject
 * @throws IllegalArgumentException if the name of the subject is null or blank
 * @throws IllegalArgumentException if the credits are not positive
 * @throws IllegalArgumentException if the rating is not in its bounds
 * @throws IllegalArgumentException if the Category is null
 * @throws IllegalArgumentException if the neededStudy time is not positive
 */
public record UniversitySubject(String name, int credits, int rating, Category category, int neededStudyTime) {
    public UniversitySubject {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must be not blank!");
        }
        if (rating < 1) {
            throw new IllegalArgumentException("Credits must be positive!");
        }
        if (rating > 5) {
            throw new IllegalArgumentException("Credits must be between 1-5!");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category must not be null!");
        }
        if (neededStudyTime < 1) {
            throw new IllegalArgumentException("Needed study time must be positive!");
        }
    }
}