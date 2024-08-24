package com.socialnetwork.post.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreationResponseDTO {
    String id;
    String content;
    Date createdDate;
    Date modifiedDate;
}
