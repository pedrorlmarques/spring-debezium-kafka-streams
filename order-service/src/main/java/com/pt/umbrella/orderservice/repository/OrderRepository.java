package com.pt.umbrella.orderservice.repository;

import com.pt.umbrella.orderservice.domain.Orders;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends R2dbcRepository<Orders, Integer> {
}
