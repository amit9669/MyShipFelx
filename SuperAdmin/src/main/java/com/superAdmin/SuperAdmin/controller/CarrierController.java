package com.superAdmin.SuperAdmin.controller;

import com.superAdmin.SuperAdmin.entity.request.CarrierRequest;
import com.superAdmin.SuperAdmin.entity.response.CustomException;
import com.superAdmin.SuperAdmin.service.ICarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/carrier")
public class CarrierController {

    @Autowired
    private ICarrierService iCarrierService;

    @PostMapping("/saveOrUpdateCarrier")
    public ResponseEntity<?> saveOrUpdateCarrier(@ModelAttribute CarrierRequest carrierRequest){
        return new ResponseEntity<>(iCarrierService.saveOrUpdateCarrier(carrierRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getAllCarrier")
    public ResponseEntity<?> getAllCarrier(@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
                                           @RequestParam(value = "pageSize",defaultValue = "30",required = false)Integer pageSize){
        Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
        return new ResponseEntity<>(iCarrierService.getAllCarrier(pageable),HttpStatus.OK);
    }

    @PostMapping("/deleteCarrier/{id}")
    public ResponseEntity<?> deleteCarrier(@PathVariable Long id){
        try{
            return new ResponseEntity<>(iCarrierService.deleteCarrier(id),HttpStatus.OK);
        }catch(CustomException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
