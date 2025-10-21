package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class PublicVoteEliminationRule implements EliminationRule {
    private String[] votes;

    public PublicVoteEliminationRule(String[] votes) {
        this.votes = votes;
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        String nameOfEliminatedErgenka = eliminateErgenka(ergenkas);
        if (nameOfEliminatedErgenka == null) return ergenkas;

        return getNotEliminatedErgenkas(ergenkas, nameOfEliminatedErgenka);
    }

    private Ergenka[] getNotEliminatedErgenkas(Ergenka[] ergenkas, String nameOfEliminatedErgenka) {
        Ergenka[] notEliminatedErgenkas = new Ergenka[ergenkas.length - 1];
        int idx = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka.getName().equals(nameOfEliminatedErgenka)) continue;
            notEliminatedErgenkas[idx++] = ergenka;
        }

        return notEliminatedErgenkas;
    }

    private String eliminateErgenka(Ergenka[] ergenkas) {
        String nameOfErgenka = null;
        int count = 0;
        for (String vote : votes) {
            if (count == 0) {
                nameOfErgenka = vote;
                count++;
            } else if (nameOfErgenka.equals(vote)) {
                count++;
            } else {
                count--;
            }
        }

        if (nameOfErgenka == null || !isVoteMajority(nameOfErgenka, ergenkas)) {
            return null;
        }

        return nameOfErgenka;
    }

    private boolean isVoteMajority(String nameOfErgenka, Ergenka[] ergenkas) {
        int allVotesForErgenka = 0;
        for (String vote : votes) {
            if (vote.equals(nameOfErgenka)) {
                allVotesForErgenka++;
            }
        }

        return allVotesForErgenka > ergenkas.length / 2;
    }
}
