package com.msf.controller;

import com.msf.entity.request.*;
import com.msf.exception.CustomException;
import com.msf.service.IMsfCompanyService;
import com.msf.service.Impl.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/msf")
public class MsfCompanyController {

    @Autowired
    private IMsfCompanyService iMsfCompanyService;

    @Autowired
    private UserToken userToken;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUpMsfCompany(@RequestBody MsfCompanyRequest msfCompanyRequest) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.signUpMsfCompany(msfCompanyRequest), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logIn")
    public ResponseEntity<?> logIn(@RequestBody MsfLogIn msfLogIn) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.logIn(msfLogIn), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkOTP")
    public ResponseEntity<?> checkOTP(@RequestBody MsfLogIn msfLogIn) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.checkOtp(msfLogIn), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCompanyById")
    public ResponseEntity<?> getCompanyById() {
        try {
            return new ResponseEntity<>(iMsfCompanyService.getCompanyById(userToken.getCompanyFromToken().getCompanyId()),
                    HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveOrUpdateUser")
    public ResponseEntity<?> saveOrUpdateUser(@RequestBody UserRequest userRequest) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.saveOrUpdate(userRequest), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllUserByCompanyId")
    public ResponseEntity<?> getAllUserByCompanyId(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", defaultValue = "30", required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
        return new ResponseEntity<>(iMsfCompanyService.getAllUserByCompanyId(pageable), HttpStatus.OK);
    }

    @PostMapping("/saveOrUpdatePaymentDetails")
    public ResponseEntity<?> saveOrUpdatePaymentDetails(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(iMsfCompanyService.saveOrUpdatePaymentDetails(paymentRequest), HttpStatus.CREATED);
    }

    @PostMapping("/saveOrUpdateStoreDetails")
    public ResponseEntity<?> saveOrUpdateStoreDetails(@RequestBody StoreDetailsRequest storeRequest) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.saveOrUpdateStoreDetails(storeRequest), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getStoreDetailsByCompanyId")
    public ResponseEntity<?> getStoreDetailsByCompanyId(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "30", required = false) Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
            return new ResponseEntity<>(iMsfCompanyService.getStoreDetailsByCompanyId(pageable), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveMsfCompanyCarriers")
    public ResponseEntity<?> saveMsfCompanyCarriers(@RequestBody MsfCompanyCarriersRequest msfCompanyCarriersRequest) {
        try {
            Object response = iMsfCompanyService.saveMsfCompanyCarriers(msfCompanyCarriersRequest.getMsfCompanyCarrierId(), msfCompanyCarriersRequest.getCarrierIds());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllMsfCompanyCarriersByCompanyID")
    public ResponseEntity<?> getAllMsfCompanyCarriersByCompanyID(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                 @RequestParam(value = "pageSize", defaultValue = "30", required = false) Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
            return new ResponseEntity<>(iMsfCompanyService.getAllCarriersByCompanyId(pageable), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveOrUpdateBrand")
    public ResponseEntity<?> saveOrUpdateBrand(@ModelAttribute BrandRequest brandRequest) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.saveOrUpdateBrand(brandRequest), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveOrUpdateSupport")
    public ResponseEntity<?> saveOrUpdateSupport(@ModelAttribute SupportRequest supportRequest) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.saveOrUpdateSupport(supportRequest), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getSupportById/{supportId}")
    public ResponseEntity<?> getSupportById(@PathVariable Long supportId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.getBySupportId(supportId), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllSupportByCompanyId")
    public ResponseEntity<?> getAllSupportByCompanyId(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", defaultValue = "30", required = false) Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
            return new ResponseEntity<>(iMsfCompanyService.getAllSupportByCompanyId(pageable), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveOrUpdateOrderDetails")
    public ResponseEntity<?> saveOrUpdateOrderDetails(@RequestBody OrderRequest orderRequest) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.saveOrUpdateOrderDetails(orderRequest), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCommercialOrderById/{commercialOrderId}")
    public ResponseEntity<?> getCommercialOrderById(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.getCommercialOrderById(commercialOrderId), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllCommercialOrderByCompanyId")
    public ResponseEntity<?> getAllCommercialOrderByCompanyId(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                              @RequestParam(value = "pageSize", defaultValue = "30", required = false) Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
            return new ResponseEntity<>(iMsfCompanyService.getAllCommercialOrderByCompanyId(pageable), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/softDeleteCommercialOrderByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> softDeleteCommercialOrderByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.softDeleteCommercialOrderByCommercialOrderId(commercialOrderId), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteCommercialOrderByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> deleteCommercialOrderByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.deleteCommercialOrderByCommercialOrderId(commercialOrderId), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("saveOrUpdateCommercialCargoDetails")
    public ResponseEntity<?> saveOrUpdateCommercialCargoDetails(@RequestBody CommercialCargoRequest request) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.saveOrUpdateCommercialCargoDetails(request), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCommercialCargoDetailsByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> getCommercialCargoDetailsByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.getCommercialCargoDetailsByCommercialOrderId(commercialOrderId), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/softDeleteCommercialCargoDetailsByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> softDeleteCommercialCargoDetailsByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.softDeleteCommercialCargoDetailsByCommercialOrderId(commercialOrderId), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteCommercialCargoDetailsByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> deleteCommercialCargoDetailsByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.deleteCommercialCargoDetailsByCommercialOrderId(commercialOrderId), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveOrUpdateCommercialCommodityDetails")
    public ResponseEntity<?> saveOrUpdateCommercialCommodityDetails(@RequestBody List<CommercialCommodityRequest> requestList) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.saveOrUpdateCommercialCommodityDetails(requestList), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCommercialCommodityDetailsByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> getCommercialCommodityDetailsByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.getCommercialCommodityDetailsByCommercialOrderId(commercialOrderId), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/softDeleteCommercialCommodityDetailsByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> softDeleteCommercialCommodityDetailsByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.softDeleteCommercialCommodityDetailsByCommercialOrderId(commercialOrderId), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteCommercialCommodityDetailsByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> deleteCommercialCommodityDetailsByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.deleteCommercialCommodityDetailsByCommercialOrderId(commercialOrderId), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveOrUpdateCommercialInvoice")
    public ResponseEntity<?> saveOrUpdateCommercialInvoice(@RequestBody CommercialInvoiceRequest commercialInvoiceRequest) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.saveOrUpdateCommercialInvoice(commercialInvoiceRequest), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCommercialInvoiceByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> getCommercialInvoiceByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.getCommercialInvoiceByCommercialOrderId(commercialOrderId), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/getCommercialInvoiceIsCancel/{commercialOrderId}")
    public ResponseEntity<?> getCommercialInvoiceIsCancel(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.getCommercialInvoiceIsCancel(commercialOrderId), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteCommercialInvoiceByCommercialOrderId/{commercialOrderId}")
    public ResponseEntity<?> deleteCommercialInvoiceByCommercialOrderId(@PathVariable Long commercialOrderId) {
        try {
            return new ResponseEntity<>(iMsfCompanyService.deleteCommercialInvoiceByCommercialOrderId(commercialOrderId), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllCommercialDetails")
    public ResponseEntity<?> getAllCommercialDetails() {
        try {
            return new ResponseEntity<>(iMsfCompanyService.getAllCommercialDetails(), HttpStatus.FOUND);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/searchFilterOfCommercialInvoices")
    public ResponseEntity<?> searchFilterOfCommercialInvoices(@RequestBody ShippingFilterRequest filterRequest, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                              @RequestParam(value = "pageSize", defaultValue = "30", required = false) Integer pageSize) {
        try {
            Pageable pageable = PageRequest.of(Optional.ofNullable(pageNumber).orElse(0), Optional.ofNullable(pageSize).orElse(10));
            return new ResponseEntity<>(iMsfCompanyService.searchFilterOfCommercialInvoices(filterRequest,pageable),HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}