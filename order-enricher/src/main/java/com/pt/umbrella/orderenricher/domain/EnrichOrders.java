package com.pt.umbrella.orderenricher.domain;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;


@Value
@Builder
@ToString
public class EnrichOrders {

    Orders orders;
    Customer customer;
    Product product;
}
