package com.pt.umbrella.orderenricher.domain;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class CustomerOrder {

    Orders orders;
    Customer customer;
}
