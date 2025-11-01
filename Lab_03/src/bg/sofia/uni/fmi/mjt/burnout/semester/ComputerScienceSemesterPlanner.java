package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public final class ComputerScienceSemesterPlanner extends AbstractSemesterPlanner {

    @Override
    public UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan)
            throws InvalidSubjectRequirementsException {
        checkIsSemesterPlanAlright(semesterPlan);

        UniversitySubject[] subjects = semesterPlan.subjects();
        sortSubjectsBasedOnRating(subjects);

        return getSubjectList(subjects, semesterPlan.minimalAmountOfCredits());
    }

    private void sortSubjectsBasedOnRating(UniversitySubject[] subjects) {
        for (int i = 0; i < subjects.length - 1; i++) {
            int ratingMax = i;
            for (int j = i + 1; j < subjects.length; j++) {
                if (subjects[ratingMax].rating() < subjects[j].rating()) {
                    ratingMax = j;
                }
            }
            UniversitySubject tmp = subjects[i];
            subjects[i] = subjects[ratingMax];
            subjects[ratingMax] = tmp;
        }
    }

    private UniversitySubject[] getSubjectList(UniversitySubject[] subjects, int minimalAmount) {
        int subjectsCount = getSubjectsCount(subjects, minimalAmount);
        UniversitySubject[] subjectList = new UniversitySubject[subjectsCount];
        for (int i = 0; i < subjectsCount; i++) {
            subjectList[i] = subjects[i];
        }

        return subjectList;
    }

    private int getSubjectsCount(UniversitySubject[] subjects, int minimalAmount) {
        int count = 0;
        int curAmount = 0;
        for (UniversitySubject subject : subjects) {
            curAmount += subject.credits();
            count++;
            if (curAmount >= minimalAmount) {
                break;
            }
        }

        return count;
    }
}