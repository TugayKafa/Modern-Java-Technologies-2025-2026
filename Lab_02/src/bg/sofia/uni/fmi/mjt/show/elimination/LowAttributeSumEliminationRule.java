package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowAttributeSumEliminationRule implements EliminationRule {
    private final int threshold;

    public LowAttributeSumEliminationRule(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if (ergenkas == null || ergenkas.length == 0) return new Ergenka[0];
        int countOfNotEliminatedErgenkas = getCountOfNotEliminatedErgenkas(ergenkas);

        return getNotEliminatedErgenkas(ergenkas, countOfNotEliminatedErgenkas);
    }

    private Ergenka[] getNotEliminatedErgenkas(Ergenka[] ergenkas, int countOfNotEliminatedErgenkas) {
        Ergenka[] notEliminatedErgenkas = new Ergenka[countOfNotEliminatedErgenkas];

        int idx = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka == null) {
                continue;
            }
            if (ergenka.getHumorLevel() + ergenka.getRomanceLevel() >= threshold) {
                notEliminatedErgenkas[idx++] = ergenka;
            }
        }
        return notEliminatedErgenkas;
    }

    private int getCountOfNotEliminatedErgenkas(Ergenka[] ergenkas) {
        int countOfEliminated = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka == null) {
                continue;
            }
            if (ergenka.getHumorLevel() + ergenka.getRomanceLevel() < threshold) {
                countOfEliminated++;
            }
        }

        return ergenkas.length - countOfEliminated;
    }
}
