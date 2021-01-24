package com.pt.umbrella.orderenricher.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    private Integer id;
    private String name;
    private Integer price;
    private Integer productId;
    private Integer customerId;

}
