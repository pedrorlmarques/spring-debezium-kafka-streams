package com.pt.umbrella.orderservice.facade;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface OrderFacade {

    Mono<ServerResponse> getOrders(ServerRequest serverRequest);

    Mono<ServerResponse> createOrder(ServerRequest serverRequest);

    Mono<ServerResponse> updateOrder(ServerRequest serverRequest);
}
