package com.jejujg.repository;

import com.jejujg.model.Goods;
import com.jejujg.model.CategoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    // image 필드를 Eager로 조회한다.
    @EntityGraph(attributePaths = "image")
    @Query(value = "select DISTINCT b from Goods b where b.categoryItem=?1")
    Page<Goods> findAllByCategoryItem(CategoryItem categoryItem, Pageable pageable);
    Page<Goods> findAllByTitleContaining(String title, Pageable pageable);
    Page<Goods> findAllByContentContaining(String content, Pageable pageable);
    Page<Goods> findAllByWriter(String writer, Pageable pageable);

    @EntityGraph(attributePaths = {"image", "categoryItem"})
    @Query(value = "select DISTINCT b from Goods b where b.gid=?1")
    Optional<Goods> findByGid(Long gid);
//    Image findImageByGid(Long gid);
//    @Query(value = "select g from Goods g where g.categoryItem=?1 or g.categoryItem=?2 or g.categoryItem=?3")
//    Page<Goods> findAllByMainCategoryItem()
}
