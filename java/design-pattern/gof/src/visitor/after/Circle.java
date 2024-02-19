package visitor.after;

public class Circle implements Shape {

    @Override
    public void accept(Device device) {
        device.print(this);
    }
}
