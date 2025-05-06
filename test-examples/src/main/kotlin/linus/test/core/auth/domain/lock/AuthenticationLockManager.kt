package linus.test.core.auth.domain.lock

import io.github.oshai.kotlinlogging.KotlinLogging
import org.redisson.api.RAtomicLong
import org.redisson.api.RBucket
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class AuthenticationLockManager(
    private val redissonClient: RedissonClient
) {
    private val logger = KotlinLogging.logger { }

    private val TRIAL_COUNT_KEY_PREFIX = "auth:trial-count"
    private val LOCK_KEY_PREFIX = "auth:lock"
    private val LOCK_THRESHOLD = 5L

    fun increaseTrialCount(email: String): Long {
        return this.getTrialCountRedissonObject(email = email)
            .incrementAndGet()
    }

    fun resetTrialCount(email: String) {
        this.getTrialCountRedissonObject(email = email)
            .delete()
    }

    private fun getTrialCountRedissonObject(email: String): RAtomicLong {
        return this.redissonClient
            .getAtomicLong("${TRIAL_COUNT_KEY_PREFIX}:${email}")
    }

    fun shouldLock(trialCount: Long): Boolean {
        return LOCK_THRESHOLD <= trialCount
    }

    fun lock(email: String) {
        this.getLockRedissonObject(email = email)
            .set(true, Duration.ofMinutes(5))
        logger.info { "Authentication (email: ${email}) is locked." }
    }

    fun unlock(email: String) {
        this.getLockRedissonObject(email = email)
            .delete()
        logger.info { "Authentication (email: ${email}) is unlocked." }
    }

    fun isLocked(email: String): Boolean {
        return this.getLockRedissonObject(email = email)
            .isExists
    }

    private fun getLockRedissonObject(email: String): RBucket<Boolean> {
        return this.redissonClient.getBucket("${LOCK_KEY_PREFIX}:${email}")
    }
}
