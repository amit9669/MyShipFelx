package com.msf.entity;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommercialAllDetails {

    private List<OrderDetails> orderDetails;
    private List<CommercialCargoDetails> cargoDetails;
    private List<CommercialCommodityDetails> commodityDetails;
    private List<CommercialInvoice> invoice;
}
