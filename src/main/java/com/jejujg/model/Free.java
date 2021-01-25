package com.jejujg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Free {

    @Id
    @Column(name = "free_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "free")
    private List<Img> imgList = new ArrayList<>();
}
