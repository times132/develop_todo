package com.jejujg.Goods.helper;

import com.jejujg.model.Goods;
import com.jejujg.model.CategoryItem;
import com.jejujg.model.Image;
import com.jejujg.model.User;
import com.jejujg.payload.request.GoodsRequest;
import com.jejujg.service.BoardService;
import com.jejujg.service.CategoryService;
import com.jejujg.service.GoodsService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GoodsTestHelper {

    private GoodsService goodsService;
    private CategoryService categoryService;

    public GoodsRequest.GoodsRequestBuilder makeBoard(User user, String title, String content, String categoryNum) {
        CategoryItem categoryItem = categoryService.findOne(categoryNum);

        return GoodsRequest.builder()
                .title(title)
                .content(content)
                .price(2345)
                .categoryItem(categoryItem)
                .writer(user.getUsername());
    }

    public Goods createBoard(User user, String title, String content, String categoryNum) {
        return goodsService.save(makeBoard(user, title, content, categoryNum).build(), null);
    }

    public Goods createBoard(User user, String title, String content, String categoryNum, Image image) {
        return goodsService.save(makeBoard(user, title, content, categoryNum).build(), image);
    }
}
