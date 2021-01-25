package com.jejujg.service;

import com.jejujg.common.Criteria;
import com.jejujg.mapper.GoodsMapper;
import com.jejujg.model.Goods;
import com.jejujg.model.CategoryItem;
import com.jejujg.model.Image;
import com.jejujg.payload.dto.GoodsList;
import com.jejujg.payload.request.GoodsRequest;
import com.jejujg.payload.response.GoodsResponse;
import com.jejujg.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsMapper mapper;

    public Page<GoodsList> list(Criteria criteria, CategoryItem categoryItem){
        Pageable pageable = PageRequest.of(criteria.getPage() - 1, criteria.getPageSize(), Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<Goods> page;

        switch (criteria.getType()){
            case "T": page = goodsRepository.findAllByTitleContaining(criteria.getKeyword(), pageable); break; // 제목
            case "C": page = goodsRepository.findAllByContentContaining(criteria.getKeyword(), pageable); break; // 내용
//            case "I": page = goodsRepository.findAllByCategory(criteria.getKeyword(), pageable); break; // 카테고리
            case "W": page = goodsRepository.findAllByWriter(criteria.getKeyword(), pageable); break; // 작성자
            default: page = goodsRepository.findAllByCategoryItem(categoryItem, pageable); break;
        }

        return new PageImpl<>(mapper.goodsEntityToListDTO(page.getContent()), pageable, page.getTotalElements());
    }

    @Transactional
    public Goods save(GoodsRequest request, Image image){

        return goodsRepository.save(mapper.goodsDtoToEntity(request, image));
    }

    @Transactional
    public GoodsResponse findOne(Long gid){
        Goods goods = goodsRepository.findByGid(gid)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 게시물 입니다."));

        return mapper.goodsEntityToDTO(goods);
    }

    @Transactional
    public Long update(Long gid, GoodsRequest request, Image image){
        Goods goods = goodsRepository.findById(gid)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 게시물 입니다."));

        goods.update(request, image);

        return gid;
    }

    @Transactional
    public Long delete(Long bid){
        if (goodsRepository.existsById(bid)){
            goodsRepository.deleteById(bid);
            return bid;
        } else {
            throw new IllegalArgumentException("존재 하지 않는 게시물 입니다.");
        }
    }

    @Transactional
    public Image findImage(Long bid){
        return goodsRepository.findByGid(bid).get().getImage();
    }
}
