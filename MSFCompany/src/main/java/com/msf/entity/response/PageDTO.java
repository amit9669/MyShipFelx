package com.msf.entity.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private Object Data;
    private Long totalPages;
    private Integer totalElements;
    private Integer pageSize;
}
