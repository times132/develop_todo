package com.jejujg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"goods", "category"})
@Builder
public class CategoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String name;

    @Column(length = 10)
    private String itemNum;

    @OneToMany(mappedBy = "categoryItem")
    private List<Goods> goods;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
