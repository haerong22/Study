package builder.after;

import java.time.LocalDate;

public class App {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
//        TourPlanBuilder builder = new DefaultTourBuilder();
//        TourPlan plan = builder.title("칸쿤 여행")
//                .nightsAndDays(2, 3)
//                .startDate(LocalDate.of(2021, 12, 24))
//                .whereToStay("리조트")
//                .addPlan(0, "체크인 하고 짐 풀기")
//                .addPlan(0, "저녁 식사")
//                .getPlan();
//
//        TourPlan longBeachTrip = builder.title("롱비치")
//                .startDate(LocalDate.of(2022, 1, 1))
//                .getPlan();
        TourDirector director = new TourDirector(new DefaultTourBuilder());
        TourPlan tourPlan = director.cancunTrip();
        TourPlan tourPlan1 = director.longBeachTrip();

        System.out.println("tourPlan = " + tourPlan);
        System.out.println("tourPlan1 = " + tourPlan1);
    }
}
