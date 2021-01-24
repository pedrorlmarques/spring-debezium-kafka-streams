package com.pt.umbrella.orderservice.router;

import com.pt.umbrella.orderservice.facade.OrderFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class OrderRouterConfig {

    private static final String API_ORDER_PATH = "/api/orders";

    @Bean
    public RouterFunction<ServerResponse> route(final OrderFacade orderFacade) {
        return RouterFunctions.route(GET(API_ORDER_PATH), orderFacade::getOrders)
                .andRoute(POST(API_ORDER_PATH)
                        .and(accept(APPLICATION_JSON)), orderFacade::createOrder);
    }
}
