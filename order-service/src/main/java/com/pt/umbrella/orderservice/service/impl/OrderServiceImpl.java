package com.pt.umbrella.orderservice.service.impl;

import com.pt.umbrella.orderservice.domain.Orders;
import com.pt.umbrella.orderservice.listener.OrderCreatedEvent;
import com.pt.umbrella.orderservice.listener.OrderUpdatedEvent;
import com.pt.umbrella.orderservice.listener.OutboxPublisher;
import com.pt.umbrella.orderservice.mapper.OrdersMapper;
import com.pt.umbrella.orderservice.repository.OrderRepository;
import com.pt.umbrella.orderservice.service.OrderDTO;
import com.pt.umbrella.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OutboxPublisher outboxPublisher;

    @Override
    public Flux<Orders> fetchOrders() {
        return this.orderRepository.findAll();
    }

    @Transactional
    @Override
    public Mono<Void> createOrder(OrderDTO orderDTO) {
        return Mono.justOrEmpty(orderDTO)
                .map(OrdersMapper.INSTANCE::toEntity)
                .flatMap(this.orderRepository::save)
                .flatMap(savedOrder -> this.outboxPublisher.fire(OrderCreatedEvent.of(savedOrder)));
    }

    @Transactional
    @Override
    public Mono<Void> updateOrder(OrderDTO orderDTO) {
        return this.orderRepository.findById(orderDTO.getId())
                .map(existingOrder -> OrdersMapper.INSTANCE.toEntityUpdate(existingOrder, orderDTO))
                .flatMap(this.orderRepository::save)
                .flatMap(updatedOrder -> this.outboxPublisher.fire(OrderUpdatedEvent.of(updatedOrder)));
    }
}
