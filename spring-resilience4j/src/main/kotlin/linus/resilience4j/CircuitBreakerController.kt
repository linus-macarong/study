package linus.resilience4j

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import linus.resilience4j.exception.IgnoredException
import linus.resilience4j.exception.NoticeableException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/circuit")
class CircuitBreakerController(
    private val circuitBreakerService: CircuitBreakerService,
) {
    @GetMapping("/api-call")
    fun apiCall(@RequestParam param: String): String {
        return this.circuitBreakerService.process(param)
    }
}

@Service
class CircuitBreakerService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @CircuitBreaker(name = SIPLE_CIRCUIT_BREAKER_CONFIG, fallbackMethod = "fallback")
    fun process(param: String): String {
        return this.callServer(param)
    }

    private fun callServer(param: String): String {
        if (param.startsWith("a")) {
            throw NoticeableException("noticeable: Server error")
        }

        if (param.startsWith("b")) {
            // fallback은 실행된다, 상태만 변하지 않음
            throw IgnoredException("ignored: Server error")
        }

        if (param.startsWith("c")) {
            Thread.sleep(3_000) // 오래 걸리는 경우
        }

        return param
    }

    private fun fallback(param: String, ex: Exception): String {
        this.logger.info("Fallback called! param: ${param} / ex: ${ex.message}")
        return "Recovered: $ex"
    }

    companion object {
        private const val SIPLE_CIRCUIT_BREAKER_CONFIG = "simpleCircuitBreakerConfig"
    }
}
