package com.example.jpablog.repository;

import com.example.jpablog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository // 생략가능
public interface UserRepository extends JpaRepository<User, Long> {








//    // select * from user where username =? and password =?;
//    User findByUsernameAndPassword(String username, String password);

//    @Query(value = "select * from user where username=?1 and password=?2", nativeQuery = true)
//    User login(String username, String password);
}
