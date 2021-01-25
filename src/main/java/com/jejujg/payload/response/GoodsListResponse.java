package com.jejujg.payload.response;

import com.jejujg.common.Pagination;
import com.jejujg.payload.dto.GoodsList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GoodsListResponse {

    private List<GoodsList> goodsList;
    private Pagination pagination;
}
