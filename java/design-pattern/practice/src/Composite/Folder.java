package Composite;

import java.util.ArrayList;
import java.util.List;

public class Folder extends Component {

    List<Component> children = new ArrayList<>();

    public Folder(String name) {
        super(name);
    }

    public boolean addComponent(Component component) {
        return children.add(component);
    }

    public boolean removeComponent(Component component){
         return children.remove(component);
    }

    public List<Component> getChildren() {
        return children;
    }
}
