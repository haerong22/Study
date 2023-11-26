package com.example.book.domain.model;

import com.example.book.domain.model.vo.BookDesc;
import com.example.book.domain.model.vo.BookStatus;
import com.example.book.domain.model.vo.Classification;
import com.example.book.domain.model.vo.Location;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    private long no;
    private String title;
    private BookDesc desc;
    private Classification classification;
    private BookStatus bookStatus;
    private Location location;

    public void searchBook() {

    }

    public void enterBook() {

    }

    public void changeBookStatus() {

    }
}
