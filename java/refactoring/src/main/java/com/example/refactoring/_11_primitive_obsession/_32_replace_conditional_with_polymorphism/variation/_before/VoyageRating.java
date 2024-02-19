package com.example.refactoring._11_primitive_obsession._32_replace_conditional_with_polymorphism.variation._before;

import java.util.List;

public class VoyageRating {

    private Voyage voyage;

    private List<VoyageHistory> history;

    public VoyageRating(Voyage voyage, List<VoyageHistory> history) {
        this.voyage = voyage;
        this.history = history;
    }

    public char value() {
        final int vpf = this.voyageProfitFactor();
        final int vr = this.voyageRisk();
        final int chr = this.captainHistoryRisk();
        return (vpf * 3 > (vr + chr * 2)) ? 'A' : 'B';
    }

    private int captainHistoryRisk() {
        int result = 1;
        if (this.history.size() < 5) result += 4;
        result += this.history.stream().filter(v -> v.profit() < 0).count();
        if (this.voyage.zone().equals("china") && this.hasChinaHistory()) result -= 2;
        return Math.max(result, 0);
    }

    private int voyageRisk() {
        int result = 1;
        if (this.voyage.length() > 4) result += 2;
        if (this.voyage.length() > 8) result += this.voyage.length() - 8;
        if (List.of("china", "east-indies").contains(this.voyage.zone())) result += 4;
        return Math.max(result, 0);
    }

    private int voyageProfitFactor() {
        int result = 2;

        if (this.voyage.zone().equals("china")) result += 1;
        if (this.voyage.zone().equals("east-indies")) result +=1 ;
        if (this.voyage.zone().equals("china") && this.hasChinaHistory()) {
            result += 3;
            if (this.history.size() > 10) result += 1;
            if (this.voyage.length() > 12) result += 1;
            if (this.voyage.length() > 18) result -= 1;
        } else {
            if (this.history.size() > 8) result +=1 ;
            if (this.voyage.length() > 14) result -= 1;
        }

        return result;
    }

    private boolean hasChinaHistory() {
        return this.history.stream().anyMatch(v -> v.zone().equals("china"));
    }


}
