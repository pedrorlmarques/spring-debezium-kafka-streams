package com.pt.umbrella.orderservice.service;

import com.pt.umbrella.orderservice.domain.Orders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    Flux<Orders> fetchOrders();

    Mono<Void> createOrder(OrderDTO orderDTO);

    Mono<Void> updateOrder(OrderDTO orderDTO);
}
