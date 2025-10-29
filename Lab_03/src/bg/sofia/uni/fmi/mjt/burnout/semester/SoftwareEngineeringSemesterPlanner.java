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

    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found!";
    public static final String COULD_NOT_FIND_SUBJECT_MESSAGE = "Could not find subject from this category!";
    public static final String CRY_TO_STUDENT_DEPARTMENT_MESSAGE = "It is not possible to satisfy category minimum enrolment!";
    public static final int MATH_CATEGORY_INDEX = 0;
    public static final int PROGRAMMING_CATEGORY_INDEX = 1;
    public static final int THEORY_CATEGORY_INDEX = 2;
    public static final int PRACTICAL_CATEGORY_INDEX = 3;
    public static final int MATRIX_COLUMNS = 4;

    @Override
    public UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan) throws InvalidSubjectRequirementsException {
        checkIsSemesterPlanAlright(semesterPlan);

        if (!isThereEnoughSubjectsToSatisfyCategories(semesterPlan)) {
            throw new CryToStudentsDepartmentException(CRY_TO_STUDENT_DEPARTMENT_MESSAGE);
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

        UniversitySubject[][] matrix = new UniversitySubject[subjectsCount][MATRIX_COLUMNS];
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

        throw new SubjectFromCategoryNotFound(COULD_NOT_FIND_SUBJECT_MESSAGE);
    }

    private void fillMatrix(UniversitySubject[][] matrix, UniversitySubject[] subjects) {
        for (int i = 0; i < subjects.length; i++) {
            int categoryIndex = getCategoryIndex(subjects[i].category());

            for (int j = 0; j < MATRIX_COLUMNS; j++) {
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
                return MATH_CATEGORY_INDEX;
            }
            case PROGRAMMING -> {
                return PROGRAMMING_CATEGORY_INDEX;
            }
            case THEORY -> {
                return THEORY_CATEGORY_INDEX;
            }
            case PRACTICAL -> {
                return PRACTICAL_CATEGORY_INDEX;
            }
        }
        throw new SubjectFromCategoryNotFound(CATEGORY_NOT_FOUND_MESSAGE);
    }

    private boolean isThereEnoughSubjectsToSatisfyCategories(SemesterPlan semesterPlan) {
        int[] countForEveryCategory = new int[MATRIX_COLUMNS];
        int categoryIndex;
        for (UniversitySubject subject : semesterPlan.subjects()) {
            categoryIndex = getCategoryIndex(subject.category());
            countForEveryCategory[categoryIndex]++;
        }

        for (SubjectRequirement subjectRequirement : semesterPlan.subjectRequirements()) {
            categoryIndex = getCategoryIndex(subjectRequirement.category());
            if (countForEveryCategory[categoryIndex] < subjectRequirement.minAmountEnrolled()) {
                return false;
            }
        }

        return true;
    }
}
