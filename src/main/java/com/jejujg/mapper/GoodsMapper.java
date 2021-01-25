package com.jejujg.mapper;

import com.jejujg.model.Goods;
import com.jejujg.model.Image;
import com.jejujg.payload.dto.GoodsList;
import com.jejujg.payload.request.GoodsRequest;
import com.jejujg.payload.response.GoodsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoodsMapper {

    @Mapping(target = "gid", ignore = true)
    @Mapping(source = "image", target = "image")
    Goods goodsDtoToEntity(GoodsRequest request, Image image);

    GoodsResponse goodsEntityToDTO(Goods goods);
    List<GoodsList> goodsEntityToListDTO(List<Goods> goodsList);
}
