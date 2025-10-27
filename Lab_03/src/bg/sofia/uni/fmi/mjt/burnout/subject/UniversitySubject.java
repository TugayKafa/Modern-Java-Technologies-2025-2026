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

    public static final String NAME_MUST_BE_NOT_BLANK_MESSAGE = "Name must be not blank!";
    public static final String CREDITS_MUST_BE_POSITIVE_MESSAGE = "Credits must be positive!";
    public static final String CREDITS_MUST_BE_BETWEEN_1_5_MESSAGE = "Credits must be between 1-5!";
    public static final String CATEGORY_MUST_NOT_BE_NULL_MESSAGE = "Category must not be null!";
    public static final String NEEDED_STUDY_TIME_MUST_BE_POSITIVE_MESSAGE = "Needed study time must be positive!";

    public UniversitySubject {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(NAME_MUST_BE_NOT_BLANK_MESSAGE);
        }
        if (rating < 1) {
            throw new IllegalArgumentException(CREDITS_MUST_BE_POSITIVE_MESSAGE);
        }
        if (rating > 5) {
            throw new IllegalArgumentException(CREDITS_MUST_BE_BETWEEN_1_5_MESSAGE);
        }
        if (category == null) {
            throw new IllegalArgumentException(CATEGORY_MUST_NOT_BE_NULL_MESSAGE);
        }
        if (neededStudyTime < 1) {
            throw new IllegalArgumentException(NEEDED_STUDY_TIME_MUST_BE_POSITIVE_MESSAGE);
        }
    }
}