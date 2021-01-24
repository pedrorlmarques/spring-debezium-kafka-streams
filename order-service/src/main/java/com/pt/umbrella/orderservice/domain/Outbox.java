package com.pt.umbrella.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Outbox {

    @Id
    private Integer id;

    @Column(value = "aggregatetype")
    private String aggregateType;

    @Column(value = "aggregateid")
    private String aggregateId;

    private String type;

    private String payload;

}
