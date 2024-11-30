package com.superAdmin.SuperAdmin.entity.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomException extends RuntimeException{

    private String message;
}
