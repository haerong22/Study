package com.example.refactoring._18_middle_man._39_replace_superclass_with_delegate._before;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Scroll extends CategoryItem {

    private LocalDate dateLastCleaned;

    public Scroll(Integer id, String title, List<String> tags, LocalDate dateLastCleaned) {
        super(id, title, tags);
        this.dateLastCleaned = dateLastCleaned;
    }

    public long daysSinceLastCleaning(LocalDate targetDate) {
        return this.dateLastCleaned.until(targetDate, ChronoUnit.DAYS);
    }
}
