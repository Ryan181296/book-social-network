package com.socialnetwork.identity.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountResponseDTO {
    String id;
    String name;
    String code;
    String description;
    Boolean isUsed;
    Date createDate;
}
