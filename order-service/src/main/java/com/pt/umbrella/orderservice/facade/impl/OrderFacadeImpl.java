package com.pt.umbrella.orderservice.facade.impl;

import com.pt.umbrella.orderservice.domain.Orders;
import com.pt.umbrella.orderservice.facade.OrderFacade;
import com.pt.umbrella.orderservice.service.OrderDTO;
import com.pt.umbrella.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;

    @Override
    public Mono<ServerResponse> getOrders(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(BodyInserters
                        .fromProducer(this.orderService.fetchOrders(), Orders.class));
    }

    @Override
    public Mono<ServerResponse> createOrder(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(OrderDTO.class)
                .flatMap(orderDTO -> ServerResponse.ok()
                        .body(BodyInserters
                                .fromProducer(this.orderService.createOrder(orderDTO), Void.class)));
    }
}
