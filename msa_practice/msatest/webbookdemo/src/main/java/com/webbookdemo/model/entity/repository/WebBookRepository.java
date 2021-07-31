package com.webbookdemo.model.entity.repository;

import com.webbookdemo.model.entity.WebBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebBookRepository extends JpaRepository<WebBook,Long> {
}
