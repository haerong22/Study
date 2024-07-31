package org.example.elsfilemanage.domain.repository;

import org.example.elsfilemanage.domain.entity.SessionFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionFileRepository extends JpaRepository<SessionFile, Long> {

    List<SessionFile> findBySessionId(Long sessionId);

    Optional<SessionFile> findTopBySessionIdOrderByIdDesc(Long sessionId);
}