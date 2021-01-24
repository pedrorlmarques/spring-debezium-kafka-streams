package com.pt.umbrella.orderservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer id;

    private String name;

    private Integer price;

    private Integer productId;

    private Integer customerId;

}
