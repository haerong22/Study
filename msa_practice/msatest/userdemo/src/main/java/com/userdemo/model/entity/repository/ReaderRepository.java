package com.userdemo.model.entity.repository;

import com.userdemo.model.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader,Long> {
}
