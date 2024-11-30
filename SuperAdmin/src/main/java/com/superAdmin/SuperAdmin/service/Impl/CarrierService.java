package com.superAdmin.SuperAdmin.service.Impl;

import com.superAdmin.SuperAdmin.entity.Carrier;
import com.superAdmin.SuperAdmin.entity.request.CarrierRequest;
import com.superAdmin.SuperAdmin.entity.request.PageDTO;
import com.superAdmin.SuperAdmin.repository.CarrierRepository;
import com.superAdmin.SuperAdmin.service.ICarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarrierService implements ICarrierService {

    @Autowired
    private CarrierRepository carrierRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @Override
    public Object saveOrUpdateCarrier(CarrierRequest carrierRequest) {
        if(carrierRepository.existsById(carrierRequest.getCarrierId())){
            Carrier carrier = carrierRepository.findById(carrierRequest.getCarrierId()).get();
            carrier.setDescription(carrierRequest.getDescription());
            carrier.setName(carrierRequest.getName());
            carrier.setLogo(imageUploadService.uploadImage(carrierRequest.getLogo()));
            carrier.setIsActive(true);
            carrier.setIsDeleted(false);
            carrierRepository.save(carrier);
            return "Updated Successfully!!";
        }else{
            Carrier carrier = new Carrier();
            carrier.setDescription(carrierRequest.getDescription());
            carrier.setName(carrierRequest.getName());
            carrier.setLogo(imageUploadService.uploadImage(carrierRequest.getLogo()));
            carrier.setIsActive(true);
            carrier.setIsDeleted(false);
            carrierRepository.save(carrier);
            return "Inserted Successfully!!";
        }
    }

    @Override
    public Object getAllCarrier(Pageable pageable) {
        List<Carrier> all = carrierRepository.findAll();
        Page<Carrier> carrierPage = new PageImpl<>(all);
        return new PageDTO(carrierPage.getContent(),carrierPage.getTotalElements(),carrierPage.getTotalPages(),carrierPage.getNumberOfElements());
    }

    @Override
    public Object deleteCarrier(Long id) {
        if(carrierRepository.existsById(id)){
            Carrier carrier = carrierRepository.findById(id).get();
            if(carrier.getIsActive()){
                carrier.setIsActive(false);
                carrier.setIsDeleted(true);
            }
            carrierRepository.save(carrier);
            return "Deleted Successfully!!!";
        }else{
            return "Id not exists!!";
        }
    }
}
