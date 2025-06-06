package optional.model;

public class Delivery {
    private String status;
    private boolean canceled;

    public Delivery(String status, boolean canceled) {
        this.status = status;
        this.canceled = canceled;
    }

    public String getStatus() {
        return status;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
