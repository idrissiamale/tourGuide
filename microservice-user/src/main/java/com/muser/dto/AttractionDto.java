package com.muser.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDto extends LocationDto {
    private String attractionName;
    private String city;
    private String state;
    private UUID attractionId;
}
