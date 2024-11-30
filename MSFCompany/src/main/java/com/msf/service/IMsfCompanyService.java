package com.msf.service;

import com.msf.entity.request.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMsfCompanyService {

    Object signUpMsfCompany(MsfCompanyRequest msfCompanyRequest);

    void sendEmail(String to, String subject, String message);

    Object logIn(MsfLogIn msfLogIn);

    Object checkOtp(MsfLogIn msfLogIn);

    Object getCompanyById(Long companyId);

    Object findByEmail(String email);

    Object saveOrUpdate(UserRequest userRequest);

    Object getAllUserByCompanyId(Pageable pageable);

    Object saveOrUpdatePaymentDetails(PaymentRequest paymentRequest);

    Object saveOrUpdateStoreDetails(StoreDetailsRequest storeRequest);

    Object getStoreDetailsByCompanyId(Pageable pageable);

    Object saveMsfCompanyCarriers(Long msfCompanyCarrierId,List<Long> carrierIds);

    Object getAllCarriersByCompanyId(Pageable pageable);

    Object saveOrUpdateBrand(BrandRequest brandRequest);

    Object saveOrUpdateSupport(SupportRequest supportRequest);

    Object getBySupportId(Long supportId);

    Object getAllSupportByCompanyId(Pageable pageable);

    Object saveOrUpdateOrderDetails(OrderRequest orderRequest);

    Object getCommercialOrderById(Long commercialOrderId);

    Object getAllCommercialOrderByCompanyId(Pageable pageable);

    Object softDeleteCommercialOrderByCommercialOrderId(Long commercialOrderId);

    Object deleteCommercialOrderByCommercialOrderId(Long commercialOrderId);

    Object saveOrUpdateCommercialCargoDetails(CommercialCargoRequest commercialCargoRequest);

    Object getCommercialCargoDetailsByCommercialOrderId(Long commercialOrderId);

    Object softDeleteCommercialCargoDetailsByCommercialOrderId(Long commercialOrderId);

    Object deleteCommercialCargoDetailsByCommercialOrderId(Long commercialOrderId);

    Object saveOrUpdateCommercialCommodityDetails(List<CommercialCommodityRequest> requestList);

    Object getCommercialCommodityDetailsByCommercialOrderId(Long commercialOrderId);

    Object softDeleteCommercialCommodityDetailsByCommercialOrderId(Long commercialOrderId);

    Object deleteCommercialCommodityDetailsByCommercialOrderId(Long commercialOrderId);

    Object saveOrUpdateCommercialInvoice(CommercialInvoiceRequest commercialInvoiceRequest);

    Object getCommercialInvoiceByCommercialOrderId(Long commercialOrderId);

    Object getCommercialInvoiceIsCancel(Long commercialOrderId);

    Object deleteCommercialInvoiceByCommercialOrderId(Long commercialOrderId);

    Object getAllCommercialDetails();

    Object searchFilterOfCommercialInvoices(ShippingFilterRequest filterRequest, Pageable pageable);
}
