package Builder;

public class LgGramBlueprint extends Blueprint {

    private Computer computer;


    public LgGramBlueprint() {
        this.computer = new Computer("default", "default", "default");
    }

    @Override
    public Computer getComputer() {
        return computer;
    }

    @Override
    public void setCpu() {
        computer.setCpu("i7");
    }

    @Override
    public void setRam() {
        computer.setRam("8g");
    }

    @Override
    public void setStorage() {
        computer.setStorage("256g ssd");
    }


}
