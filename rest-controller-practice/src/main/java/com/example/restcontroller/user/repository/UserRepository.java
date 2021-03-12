package com.example.restcontroller.user.repository;

import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    int countByEmail(String email);

    Optional<User> findByIdAndPassword(Long id, String password);
    Optional<User> findByUserNameAndPhone(String userName, String phone);

    Optional<User> findByEmail(String email);

    List<User> findByEmailContainsAndUserNameContainsAndPhoneContains(String email, String userName, String phone);

    Long countByStatus(UserStatus userStatus);

    List<User> findByRegDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select u from User u where u.regDate between :startDate and :endDate")
    List<User> findTodayUsers(LocalDateTime startDate, LocalDateTime endDate);

}
