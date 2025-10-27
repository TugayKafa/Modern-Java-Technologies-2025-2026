package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.DisappointmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.Category;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public sealed abstract class AbstractSemesterPlanner implements SemesterPlannerAPI permits ComputerScienceSemesterPlanner, SoftwareEngineeringSemesterPlanner {

    @Override
    public abstract UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan) throws InvalidSubjectRequirementsException;

    protected void checkIsSemesterPlanAlright(SemesterPlan semesterPlan) throws InvalidSubjectRequirementsException {
        if (semesterPlan == null) {
            throw new IllegalArgumentException("Cannot calculate subject list without semester plan.");
        }

        if (isCategoriesDuplicated(semesterPlan.subjectRequirements())) {
            throw new InvalidSubjectRequirementsException();
        }

        if (isStudentCrying(semesterPlan.subjects(), semesterPlan.minimalAmountOfCredits())) {
            throw new CryToStudentsDepartmentException("Student is sad!");
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
        if (maximumSlackTime < 0) {
            throw new IllegalArgumentException("Maximum slack time must be positive!");
        }

        if (semesterDuration < 0) {
            throw new IllegalArgumentException("Semester duration must be positive!");
        }

        checkSubjects(subjects);

        int countOfJars = 0;
        int studyDays = 0;
        int restDays = 0;
        for (UniversitySubject subject : subjects) {
            studyDays += subject.neededStudyTime();
            restDays += (int) Math.ceil(subject.neededStudyTime() * Category.getCoefficient(subject.category()));
        }

        if (restDays > maximumSlackTime) {
            throw new DisappointmentException("You are too lazy! Grandma is not happy!");
        }

        countOfJars += studyDays / 5;
        if (studyDays + restDays > semesterDuration) {
            countOfJars *= 2;
        }

        return countOfJars;
    }

    private void checkSubjects(UniversitySubject[] subjects) {
        if (subjects == null) {
            throw new IllegalArgumentException("Subjects list is null!");
        }

        for (UniversitySubject subject : subjects) {
            if (subject == null) {
                throw new IllegalArgumentException("Missing information for university subjects!");
            }
        }
    }
}