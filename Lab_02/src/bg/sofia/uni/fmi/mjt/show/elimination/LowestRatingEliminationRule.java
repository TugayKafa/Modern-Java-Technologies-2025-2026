package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowestRatingEliminationRule implements EliminationRule {

    public LowestRatingEliminationRule() {
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if (ergenkas == null) return new Ergenka[0];
        ergenkas = removeNullErgenkas(ergenkas);
        int lowestRating = findLowestRating(ergenkas);

        return eliminateErgenkas(ergenkas, lowestRating);
    }

    private Ergenka[] removeNullErgenkas(Ergenka[] ergenkas) {
        int count = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka == null) {
                count++;
            }
        }
        Ergenka[] withoutNullErgenkas = new Ergenka[ergenkas.length - count];
        int idx = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka != null) {
                withoutNullErgenkas[idx++] = ergenka;
            }
        }
        return withoutNullErgenkas;
    }

    private Ergenka[] eliminateErgenkas(Ergenka[] ergenkas, int lowestRating) {
        int countOfNotEliminatedErgenkas = ergenkas.length - getCountOfEliminatedErgenkas(ergenkas, lowestRating);
        Ergenka[] notEliminatedErgenkas = new Ergenka[countOfNotEliminatedErgenkas];

        int idx = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka == null) {
                continue;
            }
            if (ergenka.getRating() != lowestRating) {
                notEliminatedErgenkas[idx++] = ergenka;
            }
        }

        return notEliminatedErgenkas;
    }

    private int getCountOfEliminatedErgenkas(Ergenka[] ergenkas, int lowestRating) {
        int count = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka == null) {
                continue;
            }
            if (ergenka.getRating() == lowestRating) {
                count++;
            }
        }

        return count;
    }

    private int findLowestRating(Ergenka[] ergenkas) {
        int lowestRating = Integer.MAX_VALUE;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka == null) {
                continue;
            }
            if (lowestRating > ergenka.getRating()) {
                lowestRating = ergenka.getRating();
            }
        }

        return lowestRating;
    }
}
