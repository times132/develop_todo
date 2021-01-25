package com.jejujg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Image extends DateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;

    private String uuid;

    private String path;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "goods_id")
//    private Goods goods;

//    @Builder
//    public Image(Long fid, String uuid, String path, String fileName, Goods goods){
//        this.fid = fid;
//        this.uuid = uuid;
//        this.path = path;
//        this.fileName = fileName;
//        this.goods = goods;
//    }
}
