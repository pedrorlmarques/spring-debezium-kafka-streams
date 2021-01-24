package com.pt.umbrella.orderenricher.processor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pt.umbrella.orderenricher.domain.Customer;
import com.pt.umbrella.orderenricher.domain.CustomerOrder;
import com.pt.umbrella.orderenricher.domain.EnrichOrders;
import com.pt.umbrella.orderenricher.domain.OrderEvent;
import com.pt.umbrella.orderenricher.domain.Orders;
import com.pt.umbrella.orderenricher.domain.Product;
import io.debezium.serde.DebeziumSerdes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Log4j2
public class OrderEnricherProcessor {

    private final ObjectMapper objectMapper;

    @Bean
    public Serde<OrderEvent> debeziumPayloadOrderEvent() {
        return DebeziumSerdes.payloadJson(OrderEvent.class);
    }

    @Bean
    public Function<KStream<String, OrderEvent>,
            Function<GlobalKTable<String, Customer>,
                    Function<GlobalKTable<String, Product>, KStream<String, EnrichOrders>>>> enrichOrder() {

        return orderStream -> (
                customerTable -> (
                        productTable -> (
                                orderStream
                                        .mapValues(orderEvent -> deserialize(orderEvent.getPayload()))
                                        .leftJoin(customerTable,
                                                (orderId, order) -> order.getCustomerId().toString(),
                                                (orders, customer) -> CustomerOrder.builder()
                                                        .orders(orders)
                                                        .customer(customer != null ? customer : new Customer())
                                                        .build()
                                        )
                                        .leftJoin(productTable,
                                                (s, customerOrder) -> customerOrder.getOrders().getProductId().toString(),
                                                (customerOrder, product) -> EnrichOrders.builder()
                                                        .orders(customerOrder.getOrders())
                                                        .product(product != null ? product : new Product())
                                                        .customer(customerOrder.getCustomer())
                                                        .build()
                                        )
                                        .peek((orderId, enrichOrders) ->
                                                log.info("Product -> Key: {}, Value {}", orderId, enrichOrders))
                        )
                )
        );
    }

    private Orders deserialize(final String event) {
        try {
            return objectMapper.readValue(event, Orders.class);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't deserialize event", e);
        }
    }
}




