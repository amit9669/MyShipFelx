package com.superAdmin.SuperAdmin.service;

import com.superAdmin.SuperAdmin.entity.request.CarrierRequest;
import org.springframework.data.domain.Pageable;

public interface ICarrierService {

    Object saveOrUpdateCarrier(CarrierRequest carrierRequest);

    Object getAllCarrier(Pageable pageable);

    Object deleteCarrier(Long id);
}
