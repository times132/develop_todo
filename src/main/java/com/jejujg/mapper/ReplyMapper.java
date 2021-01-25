package com.jejujg.mapper;

import com.jejujg.model.Reply;
import com.jejujg.payload.request.ReplyRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReplyMapper {

    Reply replyDtoToEntity(ReplyRequest replyRequest);
}
