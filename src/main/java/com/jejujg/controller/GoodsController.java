package com.jejujg.controller;

import com.jejujg.common.Criteria;
import com.jejujg.common.Pagination;
import com.jejujg.model.Image;
import com.jejujg.payload.request.GoodsRequest;
import com.jejujg.payload.response.GoodsListResponse;
import com.jejujg.payload.response.GoodsResponse;
import com.jejujg.service.CategoryService;
import com.jejujg.service.BoardService;
import com.jejujg.service.GoodsService;
import com.jejujg.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    private final GoodsService goodsService;
    private final UploadService uploadService;
    private final CategoryService categoryService;

    // 세부 카테고리 제품 리스트
    @GetMapping("/{categoryNum}")
    public ResponseEntity<GoodsListResponse> goodsListGET(@PathVariable("categoryNum") String categoryNum, Criteria criteria){

        Page page = goodsService.list(criteria, categoryService.findOne(categoryNum));
        Pagination pagination = Pagination.builder()
                .criteria(criteria)
                .pageRange(criteria.getPageRange())
                .realEndPage(page.getTotalPages())
                .total(page.getTotalElements())
                .build();

        GoodsListResponse goodsListResponse = GoodsListResponse.builder()
                .goodsList(page.getContent())
                .pagination(pagination)
                .build();

        return new ResponseEntity<>(goodsListResponse, HttpStatus.OK);
    }

//    @GetMapping("/{categoryName}")
//    public ResponseEntity<ArrayList<Goods>> mainCategoryList(@PathVariable("categoryName") String categoryName){
//        ArrayList<Goods> mainCategoryGoods = categoryService.findMainCategoryGoods(categoryName);
//    }

    // 제품 상세 페이지
    @GetMapping("/detail/{gid}")
    public ResponseEntity<GoodsResponse> goodsGET(@PathVariable("gid") Long gid){
        return new ResponseEntity<>(goodsService.findOne(gid), HttpStatus.OK);
    }

    // 제품 작성
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> goodsPOST(@RequestBody GoodsRequest request){
        Image image = null;
        if (request.getImage() != null) {
            image = uploadService.saveGoodsImageDB(request.getImage());
        }

        return new ResponseEntity<>(goodsService.save(request, image).getGid(), HttpStatus.OK);
    }

    // 제품 수정
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{gid}")
    public ResponseEntity<?> goodsPUT(@PathVariable("gid") Long gid, @RequestBody GoodsRequest request){
        Image image = null;
        Image oldImage = goodsService.findImage(gid);

        if (request.getImage() != null) { // 요청에 이미지가 있을 때
            if (oldImage == null) { // 기존에 이미지가 없을 경우(삭제할 이미지가 없음)
                image = uploadService.updateGoodsImageDB(Long.valueOf(String.valueOf(request.getImage().get("fid"))), request.getImage());
            } else if(!request.getImage().get("fid").equals(oldImage.getFid())) { // 기존 이미지랑 다를 경우
                uploadService.deleteGoodsImage(oldImage.getUuid() + "_" + oldImage.getFileName(), oldImage.getPath());
                image = uploadService.updateGoodsImageDB(Long.valueOf(String.valueOf(request.getImage().get("fid"))), request.getImage());
            } else { // 변화가 없는 경우
                image = oldImage;
            }
        }else { // 이미지가 없을 경우
            if (oldImage != null){ // 기존 이미지를 삭제 할 경우
                uploadService.deleteGoodsImage(oldImage.getUuid() + "_" + oldImage.getFileName(), oldImage.getPath());
            }
        }

        return new ResponseEntity<>(goodsService.update(gid, request, image), HttpStatus.OK);
    }

    // 제품 삭제
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{gid}")
    public ResponseEntity<?> goodsDEL(@PathVariable("gid") Long gid){
        Image image = goodsService.findImage(gid);
        uploadService.deleteGoodsImage(image.getUuid() + "_" + image.getFileName(), image.getPath());

        return new ResponseEntity<>(goodsService.delete(gid), HttpStatus.OK);
    }
}
