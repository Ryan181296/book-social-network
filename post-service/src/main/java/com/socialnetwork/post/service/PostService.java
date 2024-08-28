package com.socialnetwork.post.service;

import com.socialnetwork.post.dto.request.PostCreationRequestDTO;
import com.socialnetwork.post.dto.response.PageResponse;
import com.socialnetwork.post.dto.response.PostCreationResponseDTO;
import com.socialnetwork.post.entity.PostEntity;
import com.socialnetwork.post.mapper.JsonMapper;
import com.socialnetwork.post.repository.PostRepository;
import com.socialnetwork.post.util.PostDateFormatUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    @Autowired
    PostRepository postRepository;

    public PostCreationResponseDTO create(PostCreationRequestDTO requestDTO) {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();

        var postEntity = PostEntity.builder()
                .userId(userId)
                .content(requestDTO.getContent())
                .createdDate(Date.from(Instant.now()))
                .modifiedDate(Date.from(Instant.now()))
                .build();

        var postCreatedEntity = postRepository.save(postEntity);
        return mapToPostResponseDTO(postCreatedEntity);
    }

    public PageResponse<PostCreationResponseDTO> getPosts(String searchText, int pageSize, int pageNumber) {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var sort = Sort.by("createdDate").descending();
        var pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        var pageData = postRepository.findPostsByUserId(userId, searchText, pageable);
        List<PostCreationResponseDTO> data = pageData.getContent().stream().map(this::mapToPostResponseDTO).toList();

        return PageResponse.<PostCreationResponseDTO>builder()
                .totalPages(pageData.getTotalPages())
                .totalItems(pageData.getTotalElements())
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .data(data)
                .build();
    }

    private PostCreationResponseDTO mapToPostResponseDTO(PostEntity postEntity) {
        var responseDTO = JsonMapper.map(postEntity, PostCreationResponseDTO.class);
        if (responseDTO != null) {
            responseDTO.setCreatedDateText(PostDateFormatUtil.format(postEntity.getCreatedDate()));
        }
        return responseDTO;
    }
}