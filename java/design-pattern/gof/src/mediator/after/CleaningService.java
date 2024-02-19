package mediator.after;

public class CleaningService {

    private FrontDesk frontDesk = new FrontDesk();

    public void getTowers(Integer id, int numberOfTowers) {
        String roomNumber = this.frontDesk.getRoomNumberFor(id);
        System.out.println("provide " + numberOfTowers + " to " + roomNumber);
    }
}
