package com.msf.repository;

import com.msf.entity.OrderDetails;
import com.msf.entity.response.ShipmentFilterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {

    Page<OrderDetails> findAllOrderDetailsByCompanyIdAndIsDeleted(Long companyId, boolean b, Pageable pageable);

   /* private String commercialInvoiceId;
    private String shipmentToCountry;
    private String shipmentFromCountry;
    private LocalDateTime createdAt;*/

    @Query("SELECT new com.msf.entity.response.ShipmentFilterResponse(od.commercialInvoicesId," +
            " od.shippingToCountry, od.shippingFromCountry, od.createdAt) FROM OrderDetails AS od" +
            " WHERE od.isDeleted=false AND " +
            " (COALESCE(:companyId,'')='' OR od.companyId =:companyId) AND " +
            " (COALESCE(:shipmentFromCountry,'')='' OR od.shippingFromCountry = :shipmentFromCountry) AND " +
            " (COALESCE(:shipmentToCountry,'')='' OR od.shippingToCountry = :shipmentToCountry) AND " +
            "(COALESCE(:commercialInvoicesId,'')='' OR od.commercialInvoicesId = :commercialInvoicesId ) AND" +
            " (COALESCE(:startDate,'')='' OR od.createdAt >= :startDate) AND " +
            " (COALESCE(:endDate,'')='' OR od.createdAt <= :endDate)")
    Page<ShipmentFilterResponse> getSearchFilter(Long companyId, String shipmentFromCountry, String shipmentToCountry, LocalDateTime startDate, LocalDateTime endDate,String commercialInvoicesId,Pageable pageable);

    boolean existsByCommercialInvoicesId(String s);
}
