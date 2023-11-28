package com.example.best.domain.service;

import com.example.best.domain.model.BestBook;
import com.example.best.domain.model.Item;
import com.example.best.persistence.BestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BestBookService {

    private final BestBookRepository bestBookRepository;

    public List<BestBook> getAllBooks() {
        return bestBookRepository.findAll();
    }

    public Optional<BestBook> getBookById(String id) {
        return bestBookRepository.findById(id);
    }

    public void dealBestBook(Item item) {
        BestBook bestBook = bestBookRepository.findBestBookByItem(item)
                .orElse(BestBook.register(item));

        bestBook.increase();

        saveBook(bestBook);
    }

    public BestBook saveBook(BestBook book) {
        return bestBookRepository.save(book);
    }

    public BestBook update(String id, BestBook book) {
        Optional<BestBook> bestBookOptional = bestBookRepository.findById(id);

        if (bestBookOptional.isPresent()) {
            BestBook bestBook = bestBookOptional.get();
            bestBook.update(book);

            return bestBookRepository.save(bestBook);
        }

        return null;
    }

    public boolean deleteBook(String id) {
        Optional<BestBook> bestBookOptional = bestBookRepository.findById(id);

        if (bestBookOptional.isPresent()) {
            bestBookRepository.delete(bestBookOptional.get());
            return true;
        }

        return false;
    }

}
