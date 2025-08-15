package com.bs.post.services;


import com.bs.post.dto.requests.PostRequest;
import com.bs.post.dto.responses.PageResponse;
import com.bs.post.dto.responses.PostResponse;
import com.bs.post.dto.responses.UserProfileResponse;
import com.bs.post.entities.Post;
import com.bs.post.mappers.PostMapper;
import com.bs.post.repositories.PostRepository;
import com.bs.post.repositories.httpClient.ProfileClient;
import com.bs.post.utils.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    DateTimeFormatter dateTimeFormatter;
    PostRepository postRepository;
    PostMapper postMapper;
    ProfileClient profileClient;

    public PostResponse createPost(PostRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = Post
                .builder()
                .content(request.getContent())
                .userId(authentication.getName())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .build();

        post = postRepository.save(post);

        return postMapper.toPostResponse(post);
    }

    public PageResponse<PostResponse> getMyPosts(int page, int size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        UserProfileResponse userProfile = null;

        try {
            userProfile = profileClient.getProfile(userId).getResult();
        } catch (Exception e) {
//            log.error("Error while getting user profile: ", e);
        }

        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = postRepository.findAllByUserId(userId, pageable);
        String username = userProfile != null ? userProfile.getUsername() : null;
        var postList = pageData.getContent().stream().map(post -> {
            var postResponse = postMapper.toPostResponse(post);

            postResponse.setCreatedAt(dateTimeFormatter.format(post.getCreatedDate()));
            postResponse.setUsername(username);

            return postResponse;
        }).toList();

        return PageResponse.<PostResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(postList)
                .build();
    }
}
