package com.superAdmin.SuperAdmin.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private Object Data;
    private Long totalPages;
    private Integer totalElements;
    private Integer pageSize;
}
