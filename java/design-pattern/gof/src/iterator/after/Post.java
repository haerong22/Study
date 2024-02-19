package iterator.after;

import java.time.LocalDateTime;

public class Post {

    private String title;

    private LocalDateTime createdDateTime;

    public Post(String title) {
        this.title = title;
        this.createdDateTime = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
