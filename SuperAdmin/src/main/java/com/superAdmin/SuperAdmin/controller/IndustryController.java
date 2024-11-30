package com.superAdmin.SuperAdmin.controller;

import com.superAdmin.SuperAdmin.entity.request.IndustryRequest;
import com.superAdmin.SuperAdmin.entity.response.CustomException;
import com.superAdmin.SuperAdmin.service.IIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/industry")
public class IndustryController {

    @Autowired
    private IIndustryService iIndustryService;

    @PostMapping("/saveOrUpdateIndustry")
    public ResponseEntity<?> saveOrUpdateIndustry(@RequestBody IndustryRequest industryRequest) {
        return new ResponseEntity<>(iIndustryService.saveOrUpdateIndustry(industryRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getAllIndustry")
    public ResponseEntity<?> getAllIndustry(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "30", required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
        return new ResponseEntity<>(iIndustryService.getAllIndustry(pageable),HttpStatus.OK);
    }

    @PostMapping("/deleteIndustry/{id}")
    public ResponseEntity<?> deleteIndustry(@PathVariable Long id){
        try{
            return new ResponseEntity<>(iIndustryService.deleteIndustry(id),HttpStatus.OK);
        }catch(CustomException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
