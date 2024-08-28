package com.socialnetwork.post.controller;

import com.socialnetwork.post.dto.request.PostCreationRequestDTO;
import com.socialnetwork.post.dto.response.PageResponse;
import com.socialnetwork.post.dto.response.PostCreationResponseDTO;
import com.socialnetwork.post.dto.response.ResponseObject;
import com.socialnetwork.post.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/post")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    public ResponseObject<PostCreationResponseDTO> create(@Valid @RequestBody PostCreationRequestDTO requestDTO) {
        return ResponseObject.<PostCreationResponseDTO>builder()
                .result(postService.create(requestDTO))
                .build();
    }

    @GetMapping(value = "/my-posts")
    public ResponseObject<PageResponse<PostCreationResponseDTO>> getMyPosts(@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                                                            @RequestParam(value = "searchText", required = false) String searchText) {
        return ResponseObject.<PageResponse<PostCreationResponseDTO>>builder()
                .result(postService.getPosts(searchText, pageSize, pageNumber))
                .build();
    }
}
