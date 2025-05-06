package linus.test.test

import com.redis.testcontainers.RedisContainer
import io.kotest.extensions.spring.SpringExtension
import linus.test.TestExamplesApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.lifecycle.Startables
import org.testcontainers.utility.DockerImageName

@SpringBootTest(
    classes = [TestExamplesApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@ActiveProfiles("test")
interface IntegrationTest {
    companion object {
        val kotestExtensions = listOf(
            SpringExtension, // Integrate Spring with Kotest (see https://kotest.io/docs/extensions/spring.html)
        )

        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0").apply {
            withDatabaseName("test")
            withUsername("root")
            withPassword("root")
            withEnv("TZ", "Asia/Seoul")
            withCommand("mysqld", "--character-set-server=utf8mb4")
            withReuse(true) // to speed up container startup, see https://www.testcontainers.org/features/reuse/
        }

        private val redisContainer = RedisContainer(DockerImageName.parse("redis:7.4.3-alpine")).apply {
            withReuse(true) // to speed up container startup, see https://www.testcontainers.org/features/reuse/
        }

        init {
            // for Parallel Container Startup https://java.testcontainers.org/features/advanced_options/#parallel-container-startup
            Startables
                .deepStart(mysqlContainer, redisContainer)
                .join()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerProperties(registry: DynamicPropertyRegistry) {
            this.setSpringDataJpaProperties(registry)
            this.setSpringDataRedisProperties(registry)
        }

        private fun setSpringDataJpaProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { mysqlContainer.jdbcUrl + "?useSSL=false" }
            registry.add("spring.datasource.username") { mysqlContainer.username }
            registry.add("spring.datasource.password") { mysqlContainer.password }
        }

        private fun setSpringDataRedisProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.redis.host") { redisContainer.host }
            registry.add("spring.data.redis.port") { redisContainer.firstMappedPort.toString() }
        }
    }
}
