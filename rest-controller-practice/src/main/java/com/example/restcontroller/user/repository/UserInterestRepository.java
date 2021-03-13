package com.example.restcontroller.user.repository;

import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {

    Long countByUserAndInterestUser(User user, User interestUser);
}
