package org.example.ex08;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * default value
 */
public class Exam08 {

    public Exam08(Store store) {
        String str = toLocalDateTimeString(store.getRegisteredAt());
    }

    public String toLocalDateTimeString(LocalDateTime localDateTime) {

        localDateTime = Optional.ofNullable(localDateTime).orElseGet(LocalDateTime::now);

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy MM dd"));
    }

    public static void main(String[] args) {
        Store store = new Store();

        new Exam08(store);
    }
}

class Store {

    private LocalDateTime registeredAt;

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}