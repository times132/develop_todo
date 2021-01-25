package com.jejujg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems;

//    @Builder
//    public Category(Long id, String name, List<CategoryItem> categoryItems) {
//        this.id = id;
//        this.name = name;
//        this.categoryItems = categoryItems;
//    }
}
