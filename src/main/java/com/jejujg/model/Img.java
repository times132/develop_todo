package com.jejujg.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Img {
//    @Id
//    private ChildId id;
    @Id
    @Column(name = "img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_id")
    private Free free;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

//    @Override
//    public boolean isNew() {
//        return true;
//    }
}
