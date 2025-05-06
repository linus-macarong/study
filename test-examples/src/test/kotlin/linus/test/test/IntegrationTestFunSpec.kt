package linus.test.test

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import org.springframework.beans.factory.annotation.Autowired

abstract class IntegrationTestFunSpec(body: FunSpec.() -> Unit = {}) : IntegrationTest, FunSpec(body) {
    private val logger = KotlinLogging.logger { }

    @Autowired
    private lateinit var databaseCleaner: DatabaseCleaner

    init {
        beforeTest {
            this.databaseCleaner.clean()
            this.logger.info { "Database is cleaned before integration test" }
        }
    }

    override fun extensions(): List<Extension> {
        return IntegrationTest.kotestExtensions
    }
}
