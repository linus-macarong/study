package linus.test.soft

import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

class SoftAssertionTest_AssertJ {
    @Test
    fun `soft assertion example`() {
        assertSoftly { softly ->
            softly.assertThat(1).isEqualTo(2)
            softly.assertThat(2).isEqualTo(3)
            softly.assertThat(emptyList<Int>()).hasSize(0)
        }
    }
}
