package linus.test.soft

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class SoftAssertionTest_Kotest : FunSpec( {
    test("soft assertion example") {
        assertSoftly {
            1 shouldBe 2
            2 shouldBe 3
            emptyList<Int>() shouldHaveSize 0
        }
    }
})
