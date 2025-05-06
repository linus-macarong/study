package linus.test.test

import org.redisson.api.RedissonClient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Component
class DatabaseCleaner(
    private val crudRepositories: List<JpaRepository<*, *>>,
    private val redissonClient: RedissonClient,
) {
    fun clean() {
        this.cleanMySQL()
        this.cleanRedis()
    }

    private fun cleanMySQL() {
        for (repository in crudRepositories) {
            repository.deleteAllInBatch()
        }
    }

    private fun cleanRedis() {
        this.redissonClient.keys.flushdb()
    }
}
