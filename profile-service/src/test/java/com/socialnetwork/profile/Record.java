package com.socialnetwork.profile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Record {
    String name;
    String description;
    String code;
    Boolean isUsed;
}
