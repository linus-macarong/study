package linus.test.acceptance

import io.kotest.core.spec.style.FunSpec
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import linus.test.test.IntegrationTestFunSpec
import org.springframework.boot.test.web.server.LocalServerPort

abstract class AcceptanceTest(body: FunSpec.() -> Unit = {}) : IntegrationTestFunSpec(body) {
    @LocalServerPort
    private var port: Int? = null

    init {
        beforeSpec {
            this.configRestAssured()
        }
    }

    private fun configRestAssured() {
        if (RestAssured.requestSpecification != null) {
            return
        }

        RestAssured.requestSpecification = RequestSpecBuilder()
            .setPort(this.port!!)
            .setContentType(ContentType.JSON)
            .build()

        RestAssured.filters(
            RequestLoggingFilter(),
            ResponseLoggingFilter(),
        )
    }
}
