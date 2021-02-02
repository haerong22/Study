package com.example.jpablog.repository;

import com.example.jpablog.dto.ReplySaveRequestDto;
import com.example.jpablog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value =
            "insert into reply(user_id, board_id, content, create_date) " +
            "values(:#{#dto.userId}, :#{#dto.boardId}, :#{#dto.content}, now())", nativeQuery = true)
    void replySave(@Param("dto")ReplySaveRequestDto replySaveRequestDto);
}
