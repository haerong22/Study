package org.example.elsuser.domain.repository;

import org.example.elsuser.domain.entity.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Integer> {
}