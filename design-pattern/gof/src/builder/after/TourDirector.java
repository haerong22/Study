package builder.after;

import java.time.LocalDate;

public class TourDirector {

    private final TourPlanBuilder tourPlanBuilder;

    public TourDirector(TourPlanBuilder tourPlanBuilder) {
        this.tourPlanBuilder = tourPlanBuilder;
    }

    public TourPlan cancunTrip() throws InstantiationException, IllegalAccessException {
        TourPlanBuilder builder = tourPlanBuilder.getClass().newInstance();
        return builder.title("칸쿤 여행")
                .nightsAndDays(2, 3)
                .startDate(LocalDate.of(2021, 12, 24))
                .whereToStay("리조트")
                .addPlan(0, "체크인 하고 짐 풀기")
                .addPlan(0, "저녁 식사")
                .getPlan();
    }

    public TourPlan longBeachTrip() throws InstantiationException, IllegalAccessException {
        TourPlanBuilder builder = tourPlanBuilder.getClass().newInstance();
        return builder.title("롱비치")
                .startDate(LocalDate.of(2022, 1, 1))
                .getPlan();
    }
}
