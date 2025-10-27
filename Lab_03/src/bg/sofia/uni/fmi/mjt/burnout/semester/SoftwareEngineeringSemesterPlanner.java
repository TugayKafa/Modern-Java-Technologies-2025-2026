package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.exception.SubjectFromCategoryNotFound;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.Category;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

import java.util.Arrays;

public final class SoftwareEngineeringSemesterPlanner extends AbstractSemesterPlanner {
    @Override
    public UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan) throws InvalidSubjectRequirementsException {
        checkIsSemesterPlanAlright(semesterPlan);

        if (!isThereEnoughSubjectsToSatisfyCategories(semesterPlan)) {
            throw new CryToStudentsDepartmentException("It is not possible to satisfy category minimum enrolment!");
        }

        sortSubjectsBasedOnCredits(semesterPlan.subjects());

        return getSubjectsList(semesterPlan);
    }

    private void sortSubjectsBasedOnCredits(UniversitySubject[] subjects) {
        for (int i = 0; i < subjects.length - 1; i++) {
            int creditsMax = i;
            for (int j = i + 1; j < subjects.length; j++) {
                if (subjects[creditsMax].credits() < subjects[j].credits()) {
                    creditsMax = j;
                }
            }
            UniversitySubject tmp = subjects[i];
            subjects[i] = subjects[creditsMax];
            subjects[creditsMax] = tmp;
        }
    }

    private UniversitySubject[] getSubjectsList(SemesterPlan semesterPlan) {
        int subjectsCount = semesterPlan.subjects().length;
        UniversitySubject[] subjectsList = new UniversitySubject[subjectsCount];

        UniversitySubject[][] matrix = new UniversitySubject[subjectsCount][4];
        fillMatrix(matrix, semesterPlan.subjects());

        return getSubjectList(subjectsList, matrix, semesterPlan);
    }

    private UniversitySubject[] getSubjectList(UniversitySubject[] subjectsList, UniversitySubject[][] matrix, SemesterPlan semesterPlan) {
        int index = 0;
        int credits = 0;
        boolean[] isUsed = new boolean[matrix.length];
        for (SubjectRequirement subjectRequirement : semesterPlan.subjectRequirements()) {
            int j = getCategoryIndex(subjectRequirement.category());
            for (int i = 0; i < subjectRequirement.minAmountEnrolled(); i++) {
                subjectsList[index] = getSubject(matrix, j, isUsed);
                credits += subjectsList[index].credits();
                index++;
            }
        }

        if (credits >= semesterPlan.minimalAmountOfCredits()) {
            return Arrays.copyOfRange(subjectsList, 0, index);
        }

        for (int i = 0; i < isUsed.length; i++) {
            if (!isUsed[i]) {
                subjectsList[index++] = semesterPlan.subjects()[i];
                credits += subjectsList[index - 1].credits();
                if (credits >= semesterPlan.minimalAmountOfCredits()) break;
            }
        }

        return Arrays.copyOfRange(subjectsList, 0, index);
    }

    private UniversitySubject getSubject(UniversitySubject[][] matrix, int j, boolean[] isUsed) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][j] != null) {
                isUsed[i] = true;
                UniversitySubject subject = matrix[i][j];
                matrix[i][j] = null;
                return subject;
            }
        }

        throw new SubjectFromCategoryNotFound("Could not find subject from this category!");
    }

    private void fillMatrix(UniversitySubject[][] matrix, UniversitySubject[] subjects) {
        for (int i = 0; i < subjects.length; i++) {
            int categoryIndex = getCategoryIndex(subjects[i].category());

            for (int j = 0; j < 4; j++) {
                if (j == categoryIndex) {
                    matrix[i][j] = subjects[i];
                } else {
                    matrix[i][j] = null;
                }
            }
        }
    }

    private int getCategoryIndex(Category subject) {
        switch (subject) {
            case MATH -> {
                return 0;
            }
            case PROGRAMMING -> {
                return 1;
            }
            case THEORY -> {
                return 2;
            }
            case PRACTICAL -> {
                return 3;
            }
        }
        throw new SubjectFromCategoryNotFound("Category not found!");
    }

    private boolean isThereEnoughSubjectsToSatisfyCategories(SemesterPlan semesterPlan) {
        for (SubjectRequirement subjectRequirement : semesterPlan.subjectRequirements()) {
            int count = 0;
            for (UniversitySubject subject : semesterPlan.subjects()) {
                if (subject.category() == subjectRequirement.category()) {
                    count++;
                    if (count == subjectRequirement.minAmountEnrolled()) {
                        break;
                    }
                }
            }

            if (count < subjectRequirement.minAmountEnrolled()) {
                return false;
            }
        }

        return true;
    }
}
