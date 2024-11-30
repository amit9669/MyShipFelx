package com.msf.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MsfCompanyCarriersRequest {

    private Long msfCompanyCarrierId;
    private List<Long> carrierIds;
}
