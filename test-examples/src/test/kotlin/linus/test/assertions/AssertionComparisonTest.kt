package linus.test.assertions

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class AssertionComparisonTest : FunSpec({

    context("equality test") {
        val result = "test data"

        test("assert equality result") {
            (result == "data").shouldBeTrue()
        }

        test("assert value") {
            result shouldBe "data"
        }
    }

    context("list test") {
        val result = listOf(1, 2)

        context("equality test") {
            test("assert equality result") {
                (result == listOf(1)).shouldBeTrue()
            }

            test("assert value") {
                result shouldBe listOf(1)
            }
        }

        context("length test") {
            test("assert length equality result") {
                (result.size == 1).shouldBeTrue()
            }

            test("assert length") {
                result shouldHaveSize 1
            }
        }
    }
})
