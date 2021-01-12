package Builder;

public class ComputerFactory {

    private Blueprint blueprint;

    public void setBlueprint(Blueprint blueprint) {
        this.blueprint = blueprint;
    }

    public Computer makeAndGet() {
        blueprint.setRam();
        blueprint.setCpu();
        blueprint.setStorage();
        return blueprint.getComputer();
    }
}
 