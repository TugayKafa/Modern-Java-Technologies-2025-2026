package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.DisappointmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.Category;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public abstract sealed class AbstractSemesterPlanner implements SemesterPlannerAPI
        permits ComputerScienceSemesterPlanner, SoftwareEngineeringSemesterPlanner {

    public static final String MISSING_INFORMATION_FOR_UNIVERSITY_SUBJECTS_MESSAGE =
            "Missing information for university subjects!";
    public static final String SUBJECTS_LIST_IS_NULL_MESSAGE = "Subjects list is null!";
    public static final int MULTIPLIER_FOR_HARD_SEMESTER = 2;
    public static final int PERIOD_FOR_ONE_JAR = 5;
    public static final String DISAPPOINTMENT_EXCEPTION_MESSAGE = "You are too lazy! Grandma is not happy!";
    public static final String SEMESTER_DURATION_MUST_BE_POSITIVE_MESSAGE = "Semester duration must be positive!";
    public static final String MAXIMUM_SLACK_TIME_MUST_BE_POSITIVE_MESSAGE = "Maximum slack time must be positive!";
    public static final int MINIMUM_SLACK_TIME = 0;
    public static final int MINIMUM_SEMESTER_DURATION = 0;
    public static final String CRYING_STUDENT_MESSAGE = "Student is sad!";
    public static final String EMPTY_SEMESTER_PLAN_MESSAGE = "Cannot calculate subject list without semester plan.";

    @Override
    public abstract UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan)
            throws InvalidSubjectRequirementsException;

    protected void checkIsSemesterPlanAlright(SemesterPlan semesterPlan) throws InvalidSubjectRequirementsException {
        if (semesterPlan == null) {
            throw new IllegalArgumentException(EMPTY_SEMESTER_PLAN_MESSAGE);
        }

        if (isCategoriesDuplicated(semesterPlan.subjectRequirements())) {
            throw new InvalidSubjectRequirementsException();
        }

        if (isStudentCrying(semesterPlan.subjects(), semesterPlan.minimalAmountOfCredits())) {
            throw new CryToStudentsDepartmentException(CRYING_STUDENT_MESSAGE);
        }
    }

    protected boolean isCategoriesDuplicated(SubjectRequirement[] subjectRequirements) {
        for (int i = 0; i < subjectRequirements.length - 1; i++) {
            Category category1 = subjectRequirements[i].category();
            for (int j = i + 1; j < subjectRequirements.length; j++) {
                Category category2 = subjectRequirements[j].category();
                if (category1.name().equals(category2.name())) {
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean isStudentCrying(UniversitySubject[] subjects, int minimalAmountOfCredits) {
        int allAmount = 0;
        for (UniversitySubject subject : subjects) {
            allAmount += subject.credits();
        }

        return allAmount < minimalAmountOfCredits;
    }

    @Override
    public int calculateJarCount(UniversitySubject[] subjects, int maximumSlackTime, int semesterDuration) {
        if (maximumSlackTime < MINIMUM_SLACK_TIME) {
            throw new IllegalArgumentException(MAXIMUM_SLACK_TIME_MUST_BE_POSITIVE_MESSAGE);
        }

        if (semesterDuration < MINIMUM_SEMESTER_DURATION) {
            throw new IllegalArgumentException(SEMESTER_DURATION_MUST_BE_POSITIVE_MESSAGE);
        }

        checkSubjects(subjects);

        int countOfJars = 0;
        int studyDays = 0;
        int restDays = 0;
        for (UniversitySubject subject : subjects) {
            studyDays += subject.neededStudyTime();
            restDays += (int) Math.ceil(subject.neededStudyTime() * subject.category().getCoefficient());
        }

        if (restDays > maximumSlackTime) {
            throw new DisappointmentException(DISAPPOINTMENT_EXCEPTION_MESSAGE);
        }

        countOfJars += studyDays / PERIOD_FOR_ONE_JAR;
        if (studyDays + restDays > semesterDuration) {
            countOfJars *= MULTIPLIER_FOR_HARD_SEMESTER;
        }

        return countOfJars;
    }

    private void checkSubjects(UniversitySubject[] subjects) {
        if (subjects == null) {
            throw new IllegalArgumentException(SUBJECTS_LIST_IS_NULL_MESSAGE);
        }

        for (UniversitySubject subject : subjects) {
            if (subject == null) {
                throw new IllegalArgumentException(MISSING_INFORMATION_FOR_UNIVERSITY_SUBJECTS_MESSAGE);
            }
        }
    }
}