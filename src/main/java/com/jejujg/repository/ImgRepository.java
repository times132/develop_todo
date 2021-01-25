package com.jejujg.repository;

import com.jejujg.model.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImgRepository extends JpaRepository<Img, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from Img")
    void deleteAll();
}
