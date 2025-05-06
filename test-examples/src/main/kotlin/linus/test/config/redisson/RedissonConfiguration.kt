package linus.test.config.redisson

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfiguration(
    @Value("\${spring.data.redis.host}") private val host: String,
    @Value("\${spring.data.redis.port}") private val port: Int,
    @Value("\${spring.data.redis.database}") private val database: Int,
) {
    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer()
            .setAddress("redis://${this.host}:${this.port}")
            .setDatabase(this.database)
        return Redisson.create(config)
    }
}
