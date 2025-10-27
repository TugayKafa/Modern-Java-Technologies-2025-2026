package bg.sofia.uni.fmi.mjt.burnout.subject;

/**
 *
 * @param category          the academic category this subject belongs to
 * @param minAmountEnrolled minimum amount of subject enrolled for the category
 * @throws IllegalArgumentException if the category is null
 * @throws IllegalArgumentException if the credits are negative
 */
public record SubjectRequirement(Category category, int minAmountEnrolled) {

    public static final int MINIMUM_AMOUNT_OF_ENROLLED_SUBJECTS_FOR_STUDENT = 0;
    public static final String CATEGORY_MUST_NOT_BE_NULL_MESSAGE = "Category must not be null!";
    public static final String MIN_AMOUNT_ENROLLED_MUST_BE_NOT_NEGATIVE_MESSAGE = "Min amount enrolled must be not negative!";

    public SubjectRequirement {
        if (category == null) {
            throw new IllegalArgumentException(CATEGORY_MUST_NOT_BE_NULL_MESSAGE);
        }
        if (minAmountEnrolled < MINIMUM_AMOUNT_OF_ENROLLED_SUBJECTS_FOR_STUDENT) {
            throw new IllegalArgumentException(MIN_AMOUNT_ENROLLED_MUST_BE_NOT_NEGATIVE_MESSAGE);
        }
    }
}