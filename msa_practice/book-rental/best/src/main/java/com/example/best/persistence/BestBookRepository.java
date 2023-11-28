package com.example.best.persistence;

import com.example.best.domain.model.BestBook;
import com.example.best.domain.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BestBookRepository extends MongoRepository<BestBook, String> {

    Optional<BestBook> findBestBookByItem(Item item);
}
