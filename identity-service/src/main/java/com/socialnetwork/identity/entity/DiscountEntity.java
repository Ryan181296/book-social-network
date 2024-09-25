package com.socialnetwork.identity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity(name = "discount")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String code;
    String description;
    Boolean isUsed;
    Date createDate;
}
