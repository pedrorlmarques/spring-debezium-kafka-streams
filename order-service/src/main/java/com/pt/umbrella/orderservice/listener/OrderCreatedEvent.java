package com.pt.umbrella.orderservice.listener;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pt.umbrella.orderservice.domain.Orders;
import lombok.ToString;

@ToString
public class OrderCreatedEvent implements OutboxEvent {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final long id;
    private final JsonNode order;

    private OrderCreatedEvent(long id, JsonNode order) {
        this.id = id;
        this.order = order;
    }

    public static OrderCreatedEvent of(Orders orders) {

        var asJson = mapper.createObjectNode()
                .put("id", orders.getId())
                .put("name", orders.getName())
                .put("productId", orders.getProductId())
                .put("customerId", orders.getCustomerId())
                .put("price", orders.getPrice());

        return new OrderCreatedEvent(orders.getId(), asJson);
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(id);
    }

    @Override
    public String getAggregateType() {
        return "Order";
    }

    @Override
    public String getType() {
        return "OrderCreated";
    }

    @Override
    public JsonNode getPayload() {
        return order;
    }
}
