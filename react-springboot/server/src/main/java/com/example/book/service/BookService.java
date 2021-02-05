package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // readOnly : JPA 변경감지 내부기능 활성화 X , update 시의 정합성 유지 , insert 시의 팬텀현상은 못막음
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book 저장하기(Book book) {
        return bookRepository.save(book);
    }

    public Book 한건가져오기(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id를 확인해 주세요"));
    }

    public List<Book> 모두가져오기(){
        return bookRepository.findAll();
    }

    @Transactional
    public Book 수정하기(Long id, Book book){
        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id를 확인해 주세요"));
        bookEntity
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor());
        return bookEntity;
    } // 함수 종료 => 트랜잭션 종료 => 영속화 되어있는 데이터를 DB로 갱신(flush) => commit : 더티체킹

    @Transactional
    public String 삭제하기(Long id){
        bookRepository.deleteById(id); // 오류 발생시 익셉션 발생
        return "ok";
    }
}
