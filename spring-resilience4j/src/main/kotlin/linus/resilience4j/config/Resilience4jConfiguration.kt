package linus.resilience4j.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.core.registry.EntryAddedEvent
import io.github.resilience4j.core.registry.EntryRemovedEvent
import io.github.resilience4j.core.registry.EntryReplacedEvent
import io.github.resilience4j.core.registry.RegistryEventConsumer
import io.github.resilience4j.retry.Retry
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Resilience4jConfiguration {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun registryEventConsumerForRetry(): RegistryEventConsumer<Retry> {
        return object : RegistryEventConsumer<Retry> {
            override fun onEntryAddedEvent(entryAddedEvent: EntryAddedEvent<Retry>) {
                logger.info("RegistryEventConsumer.onEntryAddedEvent")

                entryAddedEvent.addedEntry.eventPublisher.onEvent { event ->
                    logger.info(event.toString())
                }
            }

            override fun onEntryRemovedEvent(entryRemoveEvent: EntryRemovedEvent<Retry>) {
                logger.info("RegistryEventConsumer.onEntryRemovedEvent")
            }

            override fun onEntryReplacedEvent(entryReplacedEvent: EntryReplacedEvent<Retry>) {
                logger.info("RegistryEventConsumer.onEntryReplacedEvent")
            }
        }
    }

    @Bean
    fun registryEventConsumerForCircuitBreaker(): RegistryEventConsumer<CircuitBreaker> {
        return object : RegistryEventConsumer<CircuitBreaker> {
            override fun onEntryAddedEvent(entryAddedEvent: EntryAddedEvent<CircuitBreaker>) {
                logger.info("RegistryEventConsumer.onEntryAddedEvent")

                entryAddedEvent.addedEntry.eventPublisher.onEvent { event ->
                    logger.info(event.toString())
                }
                entryAddedEvent.addedEntry.eventPublisher.onFailureRateExceeded { event ->
                    logger.info("${event.eventType}")
                }
            }

            override fun onEntryRemovedEvent(entryRemoveEvent: EntryRemovedEvent<CircuitBreaker>) {
                logger.info("RegistryEventConsumer.onEntryRemovedEvent")
            }

            override fun onEntryReplacedEvent(entryReplacedEvent: EntryReplacedEvent<CircuitBreaker>) {
                logger.info("RegistryEventConsumer.onEntryReplacedEvent")
            }
        }
    }
}
