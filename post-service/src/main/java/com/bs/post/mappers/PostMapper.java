package com.bs.post.mappers;

import com.bs.post.dto.responses.PostResponse;
import com.bs.post.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
