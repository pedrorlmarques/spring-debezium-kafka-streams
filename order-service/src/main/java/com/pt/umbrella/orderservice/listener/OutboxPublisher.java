package com.pt.umbrella.orderservice.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@Log4j2

public class OutboxPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public Mono<Void> fire(final OutboxEvent outboxEvent) {
        return Mono.fromRunnable(() -> this.applicationEventPublisher.publishEvent(outboxEvent))
                .then();
    }
}
