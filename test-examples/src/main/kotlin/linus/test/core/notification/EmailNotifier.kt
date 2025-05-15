package linus.test.core.notification

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class EmailNotifier {
    private val logger = KotlinLogging.logger { }

    fun notify(
        to: String,
        subject: String,
        content: String,
    ) {
        this.logger.info {
            "Sending email to ${to}\n" +
                "-----------------------\n" +
                "[${subject}]\n" +
                "${content}\n" +
                "-----------------------"
        }
    }
}
