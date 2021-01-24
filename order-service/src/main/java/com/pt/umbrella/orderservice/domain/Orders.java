package com.pt.umbrella.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy = "db";

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy = "db";

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();

    @Id
    private Integer id;

    private String name;

    private Integer productId;

    private Integer price;

    private Integer customerId;

}
