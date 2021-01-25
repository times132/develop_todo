package com.jejujg.model;

import com.jejujg.payload.request.GoodsRequest;
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
public class Goods extends DateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;
    private String title;
    @Lob
    private String content;
    private String writer;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryItem_id")
    private CategoryItem categoryItem;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public void update(GoodsRequest request, Image image){
        this.title = request.getTitle();
        this.content = request.getContent();
        this.price = request.getPrice();
        this.categoryItem = request.getCategoryItem();
        this.image = image;
    }
}
