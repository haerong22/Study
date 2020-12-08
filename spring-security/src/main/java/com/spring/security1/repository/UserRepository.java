package com.spring.security1.repository;

import com.spring.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CRUD 함수를 JpaRepository가 들고 있음.
// JpaRepository를 상속 했기 때문에 @Repository 어노테이션이 없어도 IoC된다.
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
