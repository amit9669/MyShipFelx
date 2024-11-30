package com.superAdmin.SuperAdmin.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IndustryRequest {

    private Long industryId;

    private String name;

    private String description;

}
