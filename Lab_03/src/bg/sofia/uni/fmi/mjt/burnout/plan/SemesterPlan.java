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
    public SemesterPlan {
        if (subjects == null) {
            throw new IllegalArgumentException("Subjects are null!");
        }
        if (subjectRequirements == null) {
            throw new IllegalArgumentException("Subjects requirements are null!");
        }
        if (minimalAmountOfCredits < 0) {
            throw new IllegalArgumentException("Minimal amount of credits must not be negative!");
        }
    }

}