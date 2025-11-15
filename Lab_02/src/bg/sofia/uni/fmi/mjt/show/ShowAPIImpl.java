package bg.sofia.uni.fmi.mjt.show;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.elimination.EliminationRule;
import bg.sofia.uni.fmi.mjt.show.elimination.LowestRatingEliminationRule;
import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class ShowAPIImpl implements ShowAPI {

    private Ergenka[] ergenkas;
    private EliminationRule[] defaultEliminationRule;

    public ShowAPIImpl(Ergenka[] ergenkas, EliminationRule[] defaultEliminationRules) {
        this.ergenkas = ergenkas;
        this.defaultEliminationRule = defaultEliminationRules;
    }

    @Override
    public Ergenka[] getErgenkas() {
        return ergenkas;
    }

    @Override
    public void playRound(DateEvent dateEvent) {
        if (ergenkas == null || ergenkas.length == 0) {
            return;
        }

        for (Ergenka ergenka : ergenkas) {
            if (ergenka == null) {
                continue;
            }
            organizeDate(ergenka, dateEvent);
        }
    }

    @Override
    public void eliminateErgenkas(EliminationRule[] eliminationRules) {
        if (eliminationRules == null || eliminationRules.length == 0) {
            if (defaultEliminationRule == null || defaultEliminationRule.length == 0) {
                eliminationRules = new EliminationRule[1];
                eliminationRules[0] = new LowestRatingEliminationRule();
            } else {
                eliminationRules = defaultEliminationRule;
            }
        }

        for (EliminationRule eliminationRule : eliminationRules) {
            if (eliminationRule == null) {
                continue;
            }
            ergenkas = eliminationRule.eliminateErgenkas(ergenkas);
        }
    }

    @Override
    public void organizeDate(Ergenka ergenka, DateEvent dateEvent) {
        if (ergenka == null) return;
        ergenka.reactToDate(dateEvent);
    }
}