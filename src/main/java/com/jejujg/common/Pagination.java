package com.jejujg.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {

    private double total;
    private Integer startPage;
    private Integer endPage;
    private Integer realEndPage;
    private boolean prev, next;

    @Builder
    public Pagination(Criteria criteria, Long total, Integer realEndPage, Integer pageRange){
        this.total = total;
        this.realEndPage = realEndPage;

        this.endPage = (int) Math.ceil(criteria.getPage() / (double) pageRange) * pageRange;
        this.startPage = endPage - pageRange + 1;

        if (this.realEndPage < this.endPage) this.endPage = this.realEndPage;

        this.prev = startPage > 1;
        this.next = this.endPage < this.realEndPage;
    }
}
