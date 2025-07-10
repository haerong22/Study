package stream;

import java.io.Serial;
import java.io.Serializable;

class DataObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int type = 0;
    private long test = 20L; // 구조 변경되어도 serialVersionUID 같다면 READ 가능(순서도 상관X)
    private final String data;

    public DataObject(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Ver: " + serialVersionUID + "\n" +
                "Data: " + type + ", " + data;
    }
}