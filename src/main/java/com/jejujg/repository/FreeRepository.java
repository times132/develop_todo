package com.jejujg.repository;

import com.jejujg.model.Free;
import com.jejujg.model.Goods;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FreeRepository extends JpaRepository<Free, Long> {

    @EntityGraph(attributePaths = "imgList")
    @Query(value = "select DISTINCT f from Free f where f.id=?1")
    Optional<Free> findById(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from Free")
    void deleteAll();
}
