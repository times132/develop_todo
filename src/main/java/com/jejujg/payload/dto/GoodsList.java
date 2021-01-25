package com.jejujg.payload.dto;

import com.jejujg.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GoodsList {

    private Long gid;
    private String title;
    private Image image;
}
