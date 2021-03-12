package com.example.restcontroller.user.repository;

import com.example.restcontroller.user.model.UserNoticeCount;
import com.example.restcontroller.user.model.UserLogCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCustomRepository {

    private final EntityManager entityManager;

    public List<UserNoticeCount> findUserNoticeCount() {
        String sql = "select u.id, u.email, u.user_name, (select count(*) from Notice n where n.user_id = u.id) notice_count from User u ";
        List<UserNoticeCount> list = entityManager.createNativeQuery(sql).getResultList();
        return list;
    }

    public List<UserLogCount> findUserLogCount() {
        String sql = "select u.id, u.email, u.user_name" +
                ", (select count(*) from notice n where n.user_id = u.id) notice_count" +
                ", (select count(*) from notice_like nl where nl.user_id = u.id) notice_like_count" +
                " from user u ";
        List<UserLogCount> list = entityManager.createNativeQuery(sql).getResultList();
        return list;
    }

    public List<UserLogCount> findUserLikeBest() {
        String sql = "select u.id, u.email, u.user_name" +
                ", (select count(*) from notice_like nl where nl.user_id = u.id) notice_like_count" +
                " from user u " +
                " order by notice_like_count desc";
        List<UserLogCount> list = entityManager.createNativeQuery(sql).getResultList();
        return list;
    }
}
