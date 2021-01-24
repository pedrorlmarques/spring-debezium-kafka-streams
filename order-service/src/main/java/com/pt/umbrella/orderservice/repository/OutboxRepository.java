package com.pt.umbrella.orderservice.repository;

import com.pt.umbrella.orderservice.domain.Outbox;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends R2dbcRepository<Outbox, Integer> {
}
