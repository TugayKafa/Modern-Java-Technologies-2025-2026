package bg.sofia.uni.fmi.mjt.burnout.plan;

import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

/**
 * Represents a requirement for the amount of subjects needed to be enrolled during the semester.
 *
 * @param subjects               the array of all subjects that a student can enroll in a given semester
 * @param subjectRequirements    the array of requirements for the subjects enrolled for the category
 * @param minimalAmountOfCredits minimum amount of subject enrolled for the category
 * @throws IllegalArgumentException if the subjects array is null
 * @throws IllegalArgumentException if the subjectRequirements array is null
 * @throws IllegalArgumentException if the minimalAmountOfCredits is negative
 */
public record SemesterPlan(UniversitySubject[] subjects, SubjectRequirement[] subjectRequirements,
                           int minimalAmountOfCredits) {

    public static final String MINIMAL_AMOUNT_OF_CREDITS_MESSAGE = "Minimal amount of credits must not be negative!";
    public static final String SUBJECTS_REQUIREMENTS_ARE_NULL_MESSAGE = "Subjects requirements are null!";
    public static final String SUBJECTS_ARE_NULL_MESSAGE = "Subjects are null!";
    public static final int MINIMUM_CREDITS = 0;

    public SemesterPlan {
        if (subjects == null) {
            throw new IllegalArgumentException(SUBJECTS_ARE_NULL_MESSAGE);
        }
        if (subjectRequirements == null) {
            throw new IllegalArgumentException(SUBJECTS_REQUIREMENTS_ARE_NULL_MESSAGE);
        }
        if (minimalAmountOfCredits < MINIMUM_CREDITS) {
            throw new IllegalArgumentException(MINIMAL_AMOUNT_OF_CREDITS_MESSAGE);
        }
    }

}