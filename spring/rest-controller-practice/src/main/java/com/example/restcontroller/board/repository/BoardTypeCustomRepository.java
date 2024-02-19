package com.example.restcontroller.board.repository;

import com.example.restcontroller.board.model.BoardTypeCount;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class BoardTypeCustomRepository {

    private final EntityManager em;

    public List<BoardTypeCount> getBoardTypeCount() {
        String sql = "select bt.id, bt.board_name, bt.reg_date, bt.using_yn, " +
                "(select count(*) from board b where b.board_type_id = bt.id) as board_count " +
                "from board_type bt where bt.using_yn=true";
//        SELECT bt.id, bt.board_name, bt.reg_date, bt.using_yn, ct.count FROM (select board_type_id, count(*) count from board group by board_type_id) ct  left outer join board_type bt on bt.id=ct.board_type_id;
//        List<BoardTypeCount> list = em.createNativeQuery(sql).getResultList();

        List<Object[]> resultList = em.createNativeQuery(sql).getResultList();
        List<BoardTypeCount> list = resultList.stream().map(BoardTypeCount::of).collect(Collectors.toList());

//        // 라이브러리 사용
//        Query nativeQuery = em.createNativeQuery(sql);
//        JpaResultMapper jpaResultMapper = new JpaResultMapper();
//        List<BoardTypeCount> list = jpaResultMapper.list(nativeQuery, BoardTypeCount.class);
        return list;
    }
}
