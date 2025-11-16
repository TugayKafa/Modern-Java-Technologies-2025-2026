package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class PublicVoteEliminationRule implements EliminationRule {
    private String[] votes;

    public PublicVoteEliminationRule(String[] votes) {
        this.votes = votes;
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if (ergenkas == null) return new Ergenka[0];
        if (votes == null || votes.length == 0) return ergenkas;
        ergenkas = removeNullErgenkas(ergenkas);
        String nameOfEliminatedErgenka = eliminateErgenka(ergenkas);
        if (nameOfEliminatedErgenka == null) return ergenkas;

        return getNotEliminatedErgenkas(ergenkas, nameOfEliminatedErgenka);
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

    private Ergenka[] getNotEliminatedErgenkas(Ergenka[] ergenkas, String nameOfEliminatedErgenka) {
        Ergenka[] notEliminatedErgenkas = new Ergenka[ergenkas.length - 1];
        int idx = 0;
        boolean isRemoved = false;

        for (Ergenka ergenka : ergenkas) {
            if (ergenka == null) continue;

            if (!isRemoved && ergenka.getName().equals(nameOfEliminatedErgenka)) {
                isRemoved = true;
                continue;
            }

            notEliminatedErgenkas[idx++] = ergenka;
        }

        return notEliminatedErgenkas;
    }

    private String eliminateErgenka(Ergenka[] ergenkas) {
        String nameOfErgenka = null;
        int count = 0;
        for (String vote : votes) {
            if (vote == null) {
                continue;
            }
            if (count == 0) {
                nameOfErgenka = vote;
                count++;
            } else if (nameOfErgenka.equals(vote)) {
                count++;
            } else {
                count--;
            }
        }

        if (nameOfErgenka == null || !exists(ergenkas, nameOfErgenka) || !isVoteMajority(nameOfErgenka, ergenkas)) {
            return null;
        }

        return nameOfErgenka;
    }

    private boolean exists(Ergenka[] ergenkas, String name) {
        for (Ergenka e : ergenkas) {
            if (e.getName().equals(name)) return true;
        }
        return false;
    }

    private boolean isVoteMajority(String nameOfErgenka, Ergenka[] ergenkas) {
        int allVotesForErgenka = 0;
        for (String vote : votes) {
            if (vote == null) {
                continue;
            }
            if (vote.equals(nameOfErgenka)) {
                allVotesForErgenka++;
            }
        }

        return allVotesForErgenka > votes.length / 2;
    }
}
