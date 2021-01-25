package com.jejujg.repository;

import com.jejujg.model.Free;
import com.jejujg.model.Notice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @EntityGraph(attributePaths = "imgList")
    @Query(value = "select DISTINCT n from Notice n where n.id=?1")
    Optional<Notice> findById(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from Notice ")
    void deleteAll();
}
