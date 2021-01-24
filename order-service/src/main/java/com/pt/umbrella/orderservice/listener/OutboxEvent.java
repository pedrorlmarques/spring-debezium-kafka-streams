package com.pt.umbrella.orderservice.listener;

import com.fasterxml.jackson.databind.JsonNode;

public interface OutboxEvent {

    String getAggregateId();

    String getAggregateType();

    String getType();

    JsonNode getPayload();
}
