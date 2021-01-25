package com.jejujg.repository;

import com.jejujg.model.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {
    Optional<CategoryItem> findByItemNum(String itemNum);
}
