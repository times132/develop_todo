package com.jejujg.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {

    private Integer page;
    private Integer pageSize;
    private Integer pageRange;
    private String type;
    private String keyword;

    public Criteria(){
        this(1, 5, 5, "","");
    }

    public Criteria(Integer page, Integer pageSize, Integer pageRange, String type, String keyword) {
        this.page = page;
        this.pageSize = pageSize;
        this.pageRange = pageRange;
        this.type = type;
        this.keyword = keyword;
    }
}
