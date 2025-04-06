package linus.resilience4j

import io.github.resilience4j.retry.annotation.Retry
import linus.resilience4j.exception.NoticeableException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/retry")
class RetryController(
    private val retryService: RetryService,
) {
    @GetMapping("/api-call")
    fun apiCall(@RequestParam param: String): String {
        return this.retryService.process(param)
    }
}

@Service
class RetryService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Retry(name = SIMPLE_RETRY_CONFIG, fallbackMethod = "fallback")
    fun process(param: String): String {
        return this.callServer(param)
    }

    private fun callServer(param: String): String {
        throw NoticeableException("noticeable: Server error")
        // throw IgnoredException("ignored: Server error")
    }

    private fun fallback(param: String, ex: Exception): String {
        this.logger.info("Fallback called! param: ${param} / ex: ${ex.message}")
        return "Recovered: $ex"
    }

    companion object {
        private const val SIMPLE_RETRY_CONFIG = "simpleRetryConfig"
    }
}
