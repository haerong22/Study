package com.userdemo.model.entity.repository;

import com.userdemo.model.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends JpaRepository<Writer,Long> {
}
