package com.pt.umbrella.orderservice.listener;

import com.pt.umbrella.orderservice.domain.Outbox;
import com.pt.umbrella.orderservice.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log4j2
public class OutboxService {

    private final OutboxRepository outboxRepository;

    @EventListener
    public void handleOutboxEvent(OutboxEvent event) {

        Mono.just(event)
                .map(e -> new Outbox(null,
                        e.getAggregateType(),
                        e.getAggregateId(),
                        e.getType(),
                        e.getPayload().toString())
                )
                .flatMap(outboxRepository::save)
                /*
                 * Delete the event once written, so that the outbox doesn't grow.
                 * The CDC eventing polls the database log entry and not the table in the database.
                 */
                .flatMap(outboxRepository::delete)
                .subscribe(null, null, () -> log.info("OutBox Event Sent {}", event));
    }
}
